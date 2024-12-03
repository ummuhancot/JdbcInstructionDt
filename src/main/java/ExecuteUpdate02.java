import java.sql.*;

public class ExecuteUpdate02 {
    //2.gün
    public static void main(String[] args) throws SQLException {
        //n02-ADIM:bağlantıyı oluşturma: db url,kullanıcı adı,password
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc_dt",
                "techpro", "password");


        //n03-ADIM:Statement oluşturma:SQL ifadelerini veritabanına
        //iletir ve çalıştırır
        Statement st = connection.createStatement();

        //ÖRNEK2:developers tablosuna yeni bir developer ekleyiniz.
        String sql1="INSERT INTO developers(name,salary,prog_lang) " +
                "VALUES('Jack Sparrow',5000,'Java')";

       //int inserted=st.executeUpdate(sql1);
       //System.out.println("Eklenen kayıt sayısı : "+inserted);

        /*//tüm kayıtları görelim.
        ResultSet rs =st.executeQuery("SELECT * FROM developers");

        while (rs.next()){

            System.out.println("id : "+rs.getInt("id")+
                    " isim : "+rs.getString("name")+" maaş : "+rs.getDouble("salary"));

        }*/

        //ÖRNEK3:developers tablosundan id'si 9 olanı siliniz.
        int deleted=st.executeUpdate("DELETE FROM developers WHERE id=9");
        System.out.println("Silinen kayıt sayısı : "+deleted);



        //ÖRNEK4:developers tablosundan id'si 14 den büyük olanları siliniz.
        int deleted2=st.executeUpdate("DELETE FROM developers WHERE id>14");
        System.out.println("Silinen kayıt sayısı 2 : "+deleted2);


        //tüm kayıtları görelim.
        ResultSet rs =st.executeQuery("SELECT * FROM developers");

        while (rs.next()){

            System.out.println("id : "+rs.getInt("id")+
                    " isim : "+rs.getString("name")+" maaş : "+rs.getDouble("salary"));

        }




    }
}
