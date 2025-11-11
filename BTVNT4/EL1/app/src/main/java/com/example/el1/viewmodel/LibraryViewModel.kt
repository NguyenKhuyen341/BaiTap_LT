package com.example.el1.viewmodel // Thay "el2" bằng tên project của bạn

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.el1.data.Book
import com.example.el1.data.SampleData
import com.example.el1.data.Student

class LibraryViewModel : ViewModel() {
    private val allStudents = SampleData.students

    // MỚI: Trạng thái cho ô nhập tên, bắt đầu bằng "Nguyễn Văn A"
    var studentNameInput = mutableStateOf("Nguyễn Văn A")

    // Trạng thái cho sinh viên đang được hiển thị
    // Bắt đầu bằng sinh viên đầu tiên
    var displayedStudent = mutableStateOf<Student?>(allStudents.first())
        private set // Chỉ cho phép ViewModel thay đổi

    // MỚI: Hàm tìm kiếm sinh viên, được gọi bởi nút "Thay đổi"
    fun searchStudentByName() {
        // Lấy tên từ ô input, xóa khoảng trắng thừa
        val nameToSearch = studentNameInput.value.trim()

        // Tìm sinh viên trong danh sách (không phân biệt hoa thường)
        val foundStudent = allStudents.find {
            it.name.equals(nameToSearch, ignoreCase = true)
        }

        // Cập nhật sinh viên được hiển thị
        // Sẽ là 'null' nếu không tìm thấy
        displayedStudent.value = foundStudent
    }

    // Hàm trả sách (logic như cũ, hoạt động trên 'displayedStudent')
    fun returnBook(book: Book) {
        val student = displayedStudent.value ?: return // Nếu ko có SV, ko làm gì

        // Tạo danh sách mới không chứa sách đã trả
        val updatedBooks = student.borrowedBooks.toMutableList().apply {
            remove(book)
        }

        // Cập nhật trạng thái bằng 1 object Student mới để Compose vẽ lại
        displayedStudent.value = student.copy(borrowedBooks = updatedBooks)
    }
}