# Vòng lặp vô hạn 
![img_4.png](img_4.png)
- Lỗi này thường xảy ra khi có một vòng lặp vô hạn trong phương thức toString() của các lớp User và Cart. Điều này có thể xảy ra nếu các lớp này tham chiếu lẫn nhau, dẫn đến việc gọi phương thức toString() liên tục mà không dừng lại.

![img_5.png](img_5.png)
- Nếu sử dụng @ToString(exclude) thì phải cố gắng loại bỏ hết toàn bộ các @OneToMany. Vì theo giải thích thì @ToString can thiệp vào việc load lazy dữ liệu từ DB, làm tắt session khiến không load lazy được.