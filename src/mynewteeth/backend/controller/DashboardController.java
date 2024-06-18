/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mynewteeth.backend.controller;

/**
 *
 * @author Us
 */
public class DashboardController {
    // View chứa Controller : View thực hiện xử lý các sự kiện và hiển thị dữ liệu có được từ Controller cung cấp
    // Controller sẽ nằm trong View , Bên trong Controller chứa các hàm xử lý Logic tương ứng với hành động của của View 
    // sau đó xử lý và trả lại dữ liệu nếu có => trả lại dữ liệu về View để cập nhật
    
    // Bài toán : Màn hình Dashboard chứa nhiều tab => nếu xử lý hết dẫn đến class này rất nặng và khó maintain.
    // Solution : Coi mỗi tab là 1 "màn hình" riêng , Mỗi màn hình nhỏ này cần 1 Sub Controller của riêng nó để xử lý logic cho riêng màn hình đó.
    
    // "Cân nhắc" : tách riêng các thuộc tính của mỗi Tab này và đưa vào xử lý tại 1 class riêng của Tab đó . Các thành phần View của tab đó được 
    // đưa vào bằng hàm get() , hoạt động giống với dependency Injection. Khi đó, các Sub Controller này sẽ không nằm trong Main Controller như bên 
    // trên mà sẽ nằm trong các class "Màn hình" tương ứng với các tab.
    // Ưu điểm : Chia nhỏ các phần => Cấu trúc rõ ràng , dễ kiểm soát và Test. Dễ áp dụng cơ chế "Lazy loading" hơn so với dồn hết code trong 1 file
    // Nhược điểm : Do các action method của View bị cố định ở class của Form => Phải sử dụng call back
}
