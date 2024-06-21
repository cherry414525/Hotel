// 當文件加載完成後執行渲染操作
$(document).ready(function() {
		// 獲取所有訂單資料的函數
		function fetchBookings() {
		    fetch('/api/bookings')  // 發送 GET 請求到 /api/bookings，假設後端提供了這個 API
		        .then(response => response.json())  // 解析 JSON 格式的回應
		        .then(bookings => {
		            console.log('所有訂單:', bookings);
		            // 清空現有的訂單列表
		            $('#booking-management tbody').empty();
		            
		            // 將每個訂單資料添加到表格中
		            bookings.forEach(booking => {
						
		                var row = '<tr>' +
		                              '<td>' + booking.bookingId + '</td>' +
		                              '<td>' + booking.roomId + '</td>' +
		                              '<td>' + booking.userName + '</td>' +
		                              '<td>' + booking.price + '</td>' +
		                              '<td>' + booking.start_date + '</td>' +
		                              '<td>' + booking.end_date + '</td>' +
		                              '<td>' + booking.status + '</td>' +
		                              '<td><button class="btn btn-sm btn-primary update-btn" data-booking-id="' + booking.booking_id + '">修改</button></td>' +
		                              '<td><button class="btn btn-sm btn-danger delete-btn" data-booking-id="' + booking.booking_id + '">刪除</button></td>' +
		                          '</tr>';
		                $('#booking-management tbody').append(row);
		            });
		        })
		        .catch(error => {
		            console.error('獲取訂單資料時發生錯誤:', error);
		        });
		}
		
		fetchBookings();  // 獲取訂單資料
 	
});