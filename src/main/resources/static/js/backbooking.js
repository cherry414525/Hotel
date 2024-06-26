// 當文件加載完成後執行渲染操作
$(document).ready(function() {
		//判斷是否登入
		 function checkSession() {
	    fetch('/api/checkSession')
	        .then(response => response.json())
	        .then(data => {
	            if (data === true) {
	                console.log('使用者已登錄');
	                // 執行使用者已登錄時的相應操作
	            } else {
	                console.log('使用者未登錄');
	                 window.location.href = 'backlogin.html';
	                // 執行使用者未登錄時的相應操作
	            }
	        })
	        .catch(error => {
	            console.error('檢查會話時發生錯誤:', error);
	        });
		}
	
	checkSession();
	
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
		        var checkIn = $('#searchCheckIn').val();
		        var status = $('#searchStatus').val();
		
		        // 建立要發送的數據對象
		        var formData = {
		            bookingId: bookingId,
		            userId: userId,
		            checkIn: checkIn,
		            status: status
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
			$('#booking-management tbody').on('click', '.update-btn', function() {
		        var bookingId = $(this).data('booking-id');
		        console.log('修改訂單 ID:', bookingId);
		        
		        fetch('/api/booking/' + bookingId)
		            .then(response => response.json())
		            .then(booking => {
						console.log("bookingId=="+booking.bookingId);
		                $('#BookingId').val(booking.bookingId);
		                $('#roomId').val(booking.roomId);
		                $('#UserName').val(booking.userName);
		                $('#totalAmount').val(booking.price);
		                $('#checkInDate').val(booking.start_date);
		                $('#checkOutDate').val(booking.end_date);
		                $('#bookingStatus').val(booking.status);
		                
		                $('#editBookingModal').modal('show');
		            })
		            .catch(error => {
		                console.error('獲取訂單失敗:', error);
		                alert('獲取訂單資料失敗！請檢查網絡連接或重試。');
	            });
	    	});
	    	
	    	//送出修改訂單
 			$('#editBookingButton').click(function() {
		        var bookingId = $('#BookingId').val();
		        var roomId = $('#roomId').val();
		        var userName = $('#UserName').val();
		        var price = $('#totalAmount').val();
		        var startDate = $('#checkInDate').val();
		        var endDate = $('#checkOutDate').val();
		        var status = $('#bookingStatus').val();
		        
		        var formData = {
		            bookingId: bookingId,
		            roomId: roomId,
		            userName: userName,
		            price: price,
		            start_date: startDate,
		            end_date: endDate,
		            status: status
		        };
		        
		        fetch('/api/updateBooking', {
		            method: 'PUT',
		            headers: {
		                'Content-Type': 'application/json'
		            },
		            body: JSON.stringify(formData)
		        })
		        .then(response => {
		            if (response.ok) {
		                console.log('訂單更新成功');
		                $('#editBookingModal').modal('hide');
		                fetchBookings(); // Refresh bookings list
		            } else {
		                throw new Error('更新訂單失敗');
		            }
		        })
		        .catch(error => {
		            console.error('更新訂單時發生錯誤:', error);
		            alert('更新訂單失敗！');
		        });
	    	});
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