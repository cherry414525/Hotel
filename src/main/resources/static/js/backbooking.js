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
		                Swal.fire({
		                    icon: 'error',
		                    title: '無法取得訂單資料!',
		                    confirmButtonText: 'OK'
		                });
		               
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
		                Swal.fire({
		                        icon: 'success',
		                        title: '修改訂單完成！',
		                        showConfirmButton: false,
		                        timer: 1500 // 1.5秒后自动关闭弹窗
		                    });
		                $('#editBookingModal').modal('hide');
		                fetchBookings(); // Refresh bookings list
		            } else {
		                throw new Error('更新訂單失敗');
		            }
		        })
		        .catch(error => {
		            console.error('更新訂單時發生錯誤:', error);
		            Swal.fire({
		                    icon: 'error',
		                    title: '更新訂單失敗！！請檢查!',
		                    confirmButtonText: 'OK'
		                });
		           
		        });
	    	});
		//--刪除訂單------------------------------------------------------------
			// 監聽刪除訂單按鈕的點擊事件（事件委派）
			$('#booking-management tbody').on('click', '.delete-btn', function() {
			    var bookingId = $(this).data('booking-id');
			    console.log('刪除訂單 ID:', bookingId);
			
			    // 使用 SweetAlert 彈窗代替 confirm
			    Swal.fire({
			        title: '確定要刪除這個訂單嗎？',
			        text: '',
			        icon: 'warning',
			        showCancelButton: true,
			        confirmButtonText: '確定',
			        cancelButtonText: '取消'
			    }).then((result) => {
			        if (result.isConfirmed) {
			            // 如果用戶確定刪除
			            fetch('/api/deleteBooking/' + bookingId, {
			                method: 'DELETE',
			            })
			            .then(response => {
			                if (response.ok) {
			                    console.log('訂單成功刪除');
			                    Swal.fire({
			                        icon: 'success',
			                        title: '已刪除！',
			                        showConfirmButton: false,
			                        timer: 1500 // 1.5秒后自动关闭弹窗
			                    });
			                    fetchBookings(); // 重新加載訂單列表的函數，用於更新 UI
			                } else {
			                    throw new Error('刪除訂單失敗');
			                }
			            })
			            .catch(error => {
			                console.error('刪除訂單時發生錯誤:', error);
			                // SweetAlert 顯示錯誤消息
			                Swal.fire({
			                    icon: 'error',
			                    title: '刪除訂單失敗！請重試。',
			                    confirmButtonText: 'OK'
			                });
			            });
			        }
			    });
			});
		//--匯出excel------------------------------------------------------------	
			// 綁定匯出按鈕的點擊事件
		    $('#exportExcelButton').click(function() {
		        exportToExcel();
		        console.log('Exporting to Excel...');
		    });
		    // 定義匯出 Excel 的函數
			function exportToExcel() {
			    // 獲取表格元素
			    var table = document.querySelector('.table');
			
			    // 將表格轉換為 workbook
			    var wb = XLSX.utils.table_to_book(table);
			
			    // 將 workbook 轉換為二進制流（Blob）
			    var wbout = XLSX.write(wb, { bookType: 'xlsx', type: 'array' });
			
			    // 生成文件名
			    var fileName = '訂單列表.xlsx';
			
			    // 創建 Blob 對象
			    var blob = new Blob([wbout], { type: 'application/octet-stream' });
			
			    // 創建一個 URL 對象
			    var url = URL.createObjectURL(blob);
			
			    // 創建一個<a>元素
			    var a = document.createElement('a');
			    a.href = url;
			    a.download = fileName;
			
			    // 模擬點擊下載
			    document.body.appendChild(a);
			    a.click();
			
			    // 刪除<a>元素
			    setTimeout(function() {
			        document.body.removeChild(a);
			        window.URL.revokeObjectURL(url);
			    }, 0);
			}
	  
	  	
});
  //--圖檔處理------------------------------------------------------------	
  // 獲取所有訂單資料的函數
		function fetchchart() {
		    return fetch('/api/bookings')  // 發送 GET 請求到 /api/bookings，假設後端提供了這個 API
		        .then(response => response.json())  // 解析 JSON 格式的回應
		        .then(bookings => {
		            console.log('所有訂單:', bookings);
		            return bookings; // 返回訂單數據以便進一步處理
		        })
		        .catch(error => {
		            console.error('獲取訂單資料時發生錯誤:', error);
		            throw error; // 處理錯誤並拋出以便上層處理
		        });
		}	
// 在文檔加載完成後執行操作
document.addEventListener('DOMContentLoaded', function() {
    fetchchart()
        .then(bookings => {
            // 以日期為key統計數量
            var dateCounts = {};
            bookings.forEach(booking => {
                var startDate = booking.start_date.split('T')[0]; // 假設日期是ISO格式，只取日期部分
                if (dateCounts[startDate]) {
                    dateCounts[startDate]++;
                } else {
                    dateCounts[startDate] = 1;
                }
            });

            // 分離出日期和數量數據
            var dates = Object.keys(dateCounts);
            var quantities = Object.values(dateCounts);

            // 使用 Chart.js 繪製長條圖
            var ctx = document.getElementById('myChart').getContext('2d');
            var myChart = new Chart(ctx, {
                type: 'bar', // 圖表類型：長條圖
                data: {
                    labels: dates, // x 軸：日期
                    datasets: [{
                        label: '訂單數量', // 數據集標籤
                        data: quantities, // y 軸：數量
                        backgroundColor: 'rgba(54, 162, 235, 0.2)', // 長條顏色
                        borderColor: 'rgba(54, 162, 235, 1)', // 長條邊框顏色
                        borderWidth: 1 // 長條邊框寬度
                    }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true // y 軸從零開始
                        }
                    }
                }
            });
        })
        .catch(error => {
            console.error('處理訂單資料時發生錯誤:', error);
        });
});