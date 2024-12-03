import java.sql.*;
//2.gün hatayı düzeltilmesi
public class Transaction02 {
    public static void main(String[] args) throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_dt",
                "techpro","password");

        Statement st =connection.createStatement();

        st.execute("CREATE TABLE IF NOT EXISTS hesaplar2 (hesap_no INT UNIQUE, isim VARCHAR(50), bakiye REAL)");

        String sql1 = "INSERT INTO hesaplar2 VALUES (?,?,?) ";
        PreparedStatement prst1 = connection.prepareStatement(sql1);
        prst1.setInt(1, 1234);
        prst1.setString(2,"Fred");
        prst1.setDouble(3,9000);
        prst1.executeUpdate();

        prst1.setInt(1, 5678);
        prst1.setString(2,"Barnie");
        prst1.setDouble(3,6000);
        prst1.executeUpdate();

        //TASK: hesap no:1234 ten hesap no:5678 e 1000$ para transferi olsun.
        //ayrı iki hesabın update işlemlerini birbiri ile bağımlı old. için
        //tek bir transaction altında toplayalım

        try {
            connection.setAutoCommit(false);
            //transaction yönetimi bizde, yeni bir transaction başlattık

            String sql = "UPDATE hesaplar2 SET bakiye=bakiye+? WHERE hesap_no=?";
            PreparedStatement prst2 = connection.prepareStatement(sql);
            //1-işlem:gönderen hesabın bakiyesi azalacak
            prst2.setDouble(1, -1000);
            prst2.setInt(2, 1234);
            prst2.executeUpdate();

//            Savepoint sp=connection.setSavepoint();
//            connection.rollback(sp);
            //işlemler arasında kayıt nok. belirleyip daha sonra
            //belirli durumda rollback ile bu noktoya dönülebilir.

            //sistemde hata oluştu kabul edelim
            if (false) {
                throw new RuntimeException();
            }


            //2-işlem:alıcı hesabın bakiyesi artacak
            prst2.setDouble(1, 1000);
            prst2.setInt(2, 5678);
            prst2.executeUpdate();

            prst2.close();

            //tüm işlemler başarılı
            System.out.println("işlem başarılı");
            connection.commit();

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Sistemde hata oluştu.");
            connection.rollback();//işlemlerden en az biri başarısız
            //o halde tüm işlemleri geri al
            //en son commit yapılan duruma döner
        }

    }
}