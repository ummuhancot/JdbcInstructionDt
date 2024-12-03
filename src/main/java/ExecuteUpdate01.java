import java.sql.*;
//2.gün
//executeUpdate():DML için kullanılır; INSERT INTO,UPDATE,DELETE
//return:(int) sorgunun sonucundan etkilenen kayıt sayısını verir
//sorgunun sonucunda etkilenen kayıt sayısını verir.
public class ExecuteUpdate01 {
    public static void main(String[] args) throws SQLException {

        //n02-ADIM:bağlantıyı oluşturma: db url,kullanıcı adı,password
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_dt",
                "techpro", "password");


        //n03-ADIM:Statement oluşturma:SQL ifadelerini veritabanına
        //iletir ve çalıştırır
        Statement st = connection.createStatement();

        //kayıtların tamamnını görelim
        System.out.println("----------------update'den önce----------------");
        ResultSet rs = st.executeQuery("SELECT * FROM developers");

        while (rs.next()) {

            System.out.println("isim : " + rs.getString("name") + " maaş : " + rs.getDouble("salary"));

        }


        //  ÖRNEK1:developers tablosunda maaşı ortalama maaştan az olanların maaşını ortalama maaş ile güncelleyiniz
        String sql1 = "UPDATE developers SET salary=(SELECT AVG(salary) FROM developers)" +
                "WHERE salary <(SELECT AVG(salary) FROM developers) ";
        int updated = st.executeUpdate(sql1);

        System.out.println("Güncellenen kayıt sayısı: " + updated);


        //kayıtların tamamnını görelim
        System.out.println("----------------update'den sonra----------------");
        ResultSet rs2 = st.executeQuery("SELECT * FROM developers");

        while (rs2.next()) {

            System.out.println("isim : " + rs2.getString("name") + " maaş : " + rs2.getDouble("salary"));

        }









    }
}