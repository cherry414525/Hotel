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
		                              '<td><button class="btn btn-sm btn-primary update-btn" data-booking-id="' + booking.bookingId + '">修改</button></td>' +
		                              '<td><button class="btn btn-sm btn-danger delete-btn" data-booking-id="' + booking.bookingId + '">刪除</button></td>' +
		                          '</tr>';
		                $('#booking-management tbody').append(row);
		            });
		        })
		        .catch(error => {
		            console.error('獲取訂單資料時發生錯誤:', error);
		        });
		}
		
		fetchBookings();  // 獲取訂單資料
		
		//--搜尋訂單------------------------------------------------------------
			// 搜尋按鈕點擊事件
		    $('#searchButton').click(function(e) {
		        e.preventDefault(); // 防止默認的表單提交行為
		        console.log('搜尋按鈕點擊');
		
		        // 獲取訂單編號和會員編號值
		        var bookingId = $('#bookingId').val();
		        var userId = $('#userId').val();
		
		        // 建立要發送的數據對象
		        var formData = {
		            bookingId: bookingId,
		            userId: userId
		        };
		
		        // 設置 Fetch 請求的選項
		        var requestOptions = {
		            method: 'POST', // 使用 POST 方法
		            headers: {
		                'Content-Type': 'application/json'
		            },
		            body: JSON.stringify(formData) // 將數據對象轉為 JSON 字符串
		        };
		
		        console.log('發送 Fetch 請求到 /api/searchbookings');
		        // 發送 Fetch 請求
		        fetch('/api/searchbookings', requestOptions)
		        .then(response => {
		            console.log('響應:', response); // 輸出整個響應對象，用於調試
		            return response.json(); // 解析 JSON 格式的響應
		        })
		        .then(bookings => {
		            console.log('訂單:', bookings); // 輸出成功解析的訂單數據，用於確認數據是否正確獲取
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
		                              '<td><button class="btn btn-sm btn-primary update-btn" data-booking-id="' + booking.bookingId + '">修改</button></td>' +
		                              '<td><button class="btn btn-sm btn-danger delete-btn" data-booking-id="' + booking.bookingId + '">刪除</button></td>' +
		                          '</tr>';
		                $('#booking-management tbody').append(row);
		            });
		
		        })
		        .catch(error => {
		            // 處理錯誤情況
		            console.error('搜尋失敗:', error);
		        });
		    });
		//--修改訂單------------------------------------------------------------
		
		//--刪除訂單------------------------------------------------------------
			// 監聽刪除訂單按鈕的點擊事件（事件委派）
			$('#booking-management tbody').on('click', '.delete-btn', function() {
			    var bookingId = $(this).data('booking-id');
			    console.log('刪除訂單 ID:', bookingId);
			    if (confirm('確定要刪除這個訂單嗎？')) {
			        fetch('/api/deleteBooking/' + bookingId, {
			            method: 'DELETE',
			        })
			        .then(response => {
			            if (response.ok) {
			                console.log('訂單成功刪除');
			                fetchBookings(); // 重新加載訂單列表的函數，用於更新 UI
			            } else {
			                throw new Error('刪除訂單失敗');
			            }
			        })
			        .catch(error => {
			            console.error('刪除訂單時發生錯誤:', error);
			            alert('刪除訂單失敗！');
			        });
			    }
			});
 	
});