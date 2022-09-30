import java.sql.*;
import java.util.Scanner;
/*
이번 과제에서는 classicmodels에 대해 JDBC 프로그램을 작성한다. 다음의 문제에 답하세요.
결과물은 구현 소스와 실행 결과 화면을 PDF로 생성하여 온라인 제출합니다.

1. Customer 테이블에 학생 본인의 정보를 입력하는 프로그램을 작성하고 그 결과를 보이세요.
 */
public class HW2_1_JDBC {
    /**
     * @param customerNumber
     * @param customerName
     * @param contactFirstName
     * @param contactLastName
     * @param phone
     * @param addressLine1
     * @param addressLine2
     * @param city
     * @param state
     * @param postalCode
     * @param country
     * @param salesRepEmployeeNumber
     * @param creditLimit
     * @return
     */
    public static void insertCustomers(int customerNumber, String customerName,
                                      String contactFirstName, String contactLastName, String phone,
                                      String addressLine1, String addressLine2, String city,
                                      String state, String postalCode, String country,
                                      int salesRepEmployeeNumber, double creditLimit){

        String sqlInsert =
                "insert into customers(customerNumber, customerName, contactFirstName, contactLastName, phone, " +
                        "addressLine1, addressLine2, city, state, postalCode, country, salesRepEmployeeNumber, creditLimit) "
                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?);";

        try(Connection conn = JDBC_Util.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
            pstmt.setInt(1, customerNumber);
            pstmt.setString(2, customerName);
            pstmt.setString(3, contactFirstName);
            pstmt.setString(4, contactLastName);
            pstmt.setString(5, phone);
            pstmt.setString(6, addressLine1);
            pstmt.setString(7, addressLine2);
            pstmt.setString(8, city);
            pstmt.setString(9, state);
            pstmt.setString(10, postalCode);
            pstmt.setString(11, country);
            pstmt.setInt(12, salesRepEmployeeNumber);
            pstmt.setDouble(13, creditLimit);

            int rowAffected = pstmt.executeUpdate();
            System.out.println("rowAffected = " + rowAffected);
            //  Statement.RETURN_GENERATED_KEYS 는 auto-increment 를 실행했을 때 발생하는 키값이다! 지금은 auto-increment가 아니
//                if(rs.next()){
//                    newID = Integer.parseInt(rs.getString("customerNumber"));
//                    System.out.println("newID = " + newID);
//                }
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int id = 0, emp_num = 0;
        String[] detail_list = new String[10];
        //cus_num, cus_name, ctct_fname, ctct_lname, phone, addr1, addr2, city, state, postCode, country
        double credit_limit = 0;
        String[] col_names = {
                "customerNumber", "customerName", "contactFirstName", "contactLastName", "phone",
                "addressLine1", "addressLine2", "city", "state", "postalCode", "country", "salesRepEmployeeNumber", "creditLimit"
        };

        for(int i=0; i<col_names.length; i++){
            System.out.println("[INPUT] "+col_names[i]);
            if(col_names[i].equals("customerNumber")){
                System.out.print("\t [FORMAT] Integer value >>");
                id = sc.nextInt();            sc.nextLine();
            }else if(col_names[i].equals("salesRepEmployeeNumber")){
                System.out.print("\t [FORMAT] Integer value >>");
                emp_num = sc.nextInt();            sc.nextLine();
            }else if(col_names[i].equals("creditLimit")){
                System.out.print("\t [FORMAT] DECIMAL(10,2) value >>");
                credit_limit = sc.nextDouble();            sc.nextLine();
            }else{
                System.out.print("\t [FORMAT] String value >>");
                detail_list[i-1] = sc.nextLine();
            }
        }

        if(id == 0){
            System.out.println("[ERROR] id value can't be 0");
            return;
        }
        insertCustomers(id, detail_list[0], detail_list[1], detail_list[2],
                detail_list[3], detail_list[4], detail_list[5], detail_list[6], detail_list[7],
                detail_list[8], detail_list[9], emp_num, credit_limit);

//        insertCustomers(32201321, "HeewonKim",
//                "Kim", "Heewon", "010-2773-0932",
//                "Gyeong-gi-do", "Republic of Korea", "Young-in",
//                "Dankook", "12345","Juk-Jeon", 1002,
//                467.12);
        /*
        mysql> show columns from customers;
+------------------------+---------------+------+-----+---------+-------+
| Field                  | Type          | Null | Key | Default | Extra |
+------------------------+---------------+------+-----+---------+-------+
| customerNumber         | int           | NO   | PRI | NULL    |       |
| customerName           | varchar(50)   | NO   |     | NULL    |       |
| contactLastName        | varchar(50)   | NO   |     | NULL    |       |
| contactFirstName       | varchar(50)   | NO   |     | NULL    |       |
| phone                  | varchar(50)   | NO   |     | NULL    |       |
| addressLine1           | varchar(50)   | NO   |     | NULL    |       |
| addressLine2           | varchar(50)   | YES  |     | NULL    |       |
| city                   | varchar(50)   | NO   |     | NULL    |       |
| state                  | varchar(50)   | YES  |     | NULL    |       |
| postalCode             | varchar(15)   | YES  |     | NULL    |       |
| country                | varchar(50)   | NO   |     | NULL    |       |
| salesRepEmployeeNumber | int           | YES  | MUL | NULL    |       |
| creditLimit            | decimal(10,2) | YES  |     | NULL    |       |
+------------------------+---------------+------+-----+---------+-------+
         */

    }
}

