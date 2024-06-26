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
	
    // 獲取所有房型名稱的函數
        function fetchRoomTypeNames() {
            fetch('/api/types')  // 發送 GET 請求到 /api/rooms/types
                .then(response => response.json())  // 解析 JSON 格式的回應
                .then(roomTypes => {
                    // 清空現有的房型選項
                    $('#roomType').empty();
                    
                     // 添加一個空選項
	                $('#roomType').append($('<option>', {
	                    value: '',
	                    text: '請選擇房型'
	                }));
	                
	                // 將每個房型名稱添加到下拉選單中
	                roomTypes.forEach(roomType => {
	                    $('#roomType').append($('<option>', {
	                        value: roomType.name,
	                        text: roomType.name
	                    }));
	                    $('#addroomType').append($('<option>', {
	                        value: roomType.name,
	                        text: roomType.name
	                    }));
	                    $('#editRoomType').append($('<option>', {
	                        value: roomType.name,
	                        text: roomType.name
	                    }));
	                }); 
	                
                })
                .catch(error => {
                    console.error('取得房型名稱時發生錯誤:', error);
                });
        }

        fetchRoomTypeNames();  // 取得房型名稱
        
     //--所有房間-------------------------------------------------------------   
       // 獲取所有房間資料的函數
    function fetchRooms() {
        fetch('/api/rooms')  // 發送 GET 請求到 /api/rooms
            .then(response => response.json())  // 解析 JSON 格式的回應
            .then(rooms => {
				 console.log('全部:', rooms);
                // 清空現有的房間列表
                $('#room-management tbody').empty();
                
                // 將每個房間資料添加到表格中
                rooms.forEach(room => {
                    var row = '<tr>' +
                                  '<td>' + room.room_id + '</td>' +
                                  '<td>' + room.type_name + '</td>' +
                                  '<td><button class="btn btn-sm btn-primary update-btn" data-room-id="' + room.room_id + '">編輯</button></td>' +
                              	  '<td><button class="btn btn-sm btn-danger delete-btn" data-room-id="' + room.room_id + '">刪除</button></td>' +
                              '</tr>';
                    $('#room-management tbody').append(row);
                });
            })
            .catch(error => {
                console.error('取得房間資料時發生錯誤:', error);
            });
    }

    fetchRooms();  // 取得房間資料
    //--所有房型-------------------------------------------------------------   
    	  // 獲取所有房間資料的函數
	    function fetchRoomtypes() {
	        fetch('/api/roomtypes')  // 發送 GET 請求到 /api/roomtypes
	            .then(response => response.json())  // 解析 JSON 格式的回應
	            .then(roomtypes => {
					 
	                // 清空現有的房間列表
	                $('#room-type-management tbody').empty();
	                
	                // 將每個房間資料添加到表格中
	                roomtypes.forEach(roomtype => {
	                    var row = '<tr>' +
	                                  '<td>' + roomtype.name + '</td>' +
	                                  '<td>' + roomtype.price + '</td>' +
	                                  '<td>' + roomtype.capacity + '</td>' +
	                                  '<td><img src="../hotel_img/' + roomtype.photo + '" alt="Room Photo" class="img-size"></td>'+
	                                  '<td>' + roomtype.total + '</td>' +
	                                  '<td><button class="btn btn-sm btn-primary update-btn" data-roomtype-id="' + roomtype.type_id + '">編輯</button></td>' +
	                              	  '<td><button class="btn btn-sm btn-danger delete-btn" data-roomtype-id="' + roomtype.type_id + '">刪除</button></td>' +
	                              '</tr>';
	                    $('#room-type-management tbody').append(row);
	                });
	            })
	            .catch(error => {
	                console.error('取得房間資料時發生錯誤:', error);
	            });
	    }
	
	    fetchRoomtypes();  // 取得房間資料
    
    //--搜尋房間--------------------------------------------------------------
    // 搜尋按鈕點擊事件
    $('#searchButton').click(function(e) {
        e.preventDefault(); // 防止默認的表單提交行為
 		console.log('Search button clicked');
        // 獲取房間編號和房型值
        var roomNumber = $('#roomNumber').val();
        var roomType = $('#roomType').val();

        // 建立要發送的數據對象
        var formData = {
            roomNumber: roomNumber,
            roomType: roomType
        };

        // 設置 Fetch 請求的選項
        var requestOptions = {
            method: 'POST', // 使用 POST 方法
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData) // 將數據對象轉為 JSON 字符串
        };
		
		 console.log('Sending fetch request to /api/search');
        // 發送 Fetch 請求
    	fetch('/api/search', requestOptions)
        .then(response => {
            console.log('Response:', response); // 输出整个响应对象，用于调试
            return response.json(); // 解析 JSON 格式的响应
        })
        .then(rooms => {
            console.log('Rooms:', rooms); // 输出成功解析的房间数据，用于确认数据是否正确获取
            // 清空現有的房間列表
            $('#room-management tbody').empty();

            // 將每個房間資料添加到表格中
            rooms.forEach(room => {
                var row = '<tr>' +
                              '<td>' + room.room_id + '</td>' +
                              '<td>' + room.type_name + '</td>' +
                              '<td><button class="btn btn-sm btn-primary update-btn" data-room-id="' + room.room_id + '">編輯</button></td>' +
                              '<td><button class="btn btn-sm btn-danger delete-btn" data-room-id="' + room.room_id + '">刪除</button></td>' +
                          '</tr>';
                $('#room-management tbody').append(row);
            });

        })
        .catch(error => {
            // 處理錯誤情況
            console.error('搜尋失敗:', error);
        });
    });
    
    //--新增房間--------------------------------------------------------------
     $('#addroomButton').on('click', function(e) {
			// 獲取輸入的房間編號和房型
		    var roomNumber = document.getElementById('addRoomNumber').value;
		    var roomType = document.getElementById('addroomType').value;
		
		    // 構建要發送的資料物件
		    var formData = {
		        roomNumber: roomNumber,
		        roomType: roomType
		    };

		    // 發送 POST 請求
		    fetch('/api/addroom', {
		        method: 'POST',
		        headers: {
		            'Content-Type': 'application/json'
		        },
		        body: JSON.stringify(formData) // 將資料物件轉為 JSON 字串
		    })
		    .then(response => {
		        if (response.ok) {
		            // 如果响應狀態碼為 200-299，解析 JSON 數據
		            return response.text(); // 返回文本數據
		        } else {
		            // 如果响應狀態碼不在 200-299 範圍內，拋出錯誤
		            throw new Error('新增房間失敗');
		        }
		    })
		    .then(message => {
		        // 根據後端返回的訊息來判斷成功或失敗
		        if (message.startsWith('新增房間成功')) {
		            console.log('新增房間成功:', message);
		            Swal.fire({
	                    icon: 'success',
	                    title: '新增房間成功！',
	                    showConfirmButton: false,
                    	timer: 1000
	                })
		            
		            document.getElementById('addRoomNumber').value = '';
		            document.getElementById('addroomType').value = '';
		             // 關閉模態框
            		$('#addRoomModal').modal('hide');
		            fetchRooms();
		        } else {
		            throw new Error('新增房間失敗');
		        }
		    })
		    .catch(error => {
		        // 處理錯誤情況
		        console.error('新增房間失敗:', error);
		        Swal.fire({
	                    icon: 'error',
	                    title: '新增房間失敗！請檢查或重試。',
	                    confirmButtonText: 'OK'
	                });
		    });
			
	 });
	 
	 //--刪除房間------------------------------------------------------------
		// 監聽刪除按鈕的點擊事件（事件委派）
		$('#room-management tbody').on('click', '.delete-btn', function() {
		    var roomId = $(this).data('room-id');
		    console.log('Delete room with ID:', roomId);
		
		    // 使用 SweetAlert 彈窗代替 confirm
		    Swal.fire({
		        title: '確定要刪除這個房間嗎？',
		        text: '請確認此房間並無訂單！',
		        icon: 'warning',
		        showCancelButton: true,
		        confirmButtonText: '確定',
		        cancelButtonText: '取消'
		    }).then((result) => {
		        if (result.isConfirmed) {
		            // 如果用戶確定刪除
		            fetch('/api/deleteRoom/' + roomId, {
		                method: 'DELETE',
		            })
		            .then(response => {
		                if (response.ok) {
							Swal.fire({
			                    icon: 'success',
			                    title: '房間成功刪除',
			                    showConfirmButton: false,
		                    	timer: 1000
			                })
		                    console.log('房間成功刪除');
		                    fetchRooms(); // 刷新房間列表
		                } else {
		                    throw new Error('刪除房間失敗');
		                }
		            })
		            .catch(error => {
		                console.error('刪除房間時發生錯誤:', error);
		                // SweetAlert 顯示錯誤消息
		                Swal.fire({
		                    icon: 'error',
		                    title: '刪除房間失敗！請檢查是否存在訂單中。',
		                    confirmButtonText: 'OK'
		                });
		            });
		        }
		    });
		});
	 //--修改房間------------------------------------------------------------
	 // 監聽刪除按鈕的點擊事件（事件委派）
	    $('#room-management tbody').on('click', '.update-btn', function() {
			var roomId = $(this).data('room-id');
			// 根據房間ID獲取房間信息
			fetch('/api/room/' + roomId)
			    .then(response => response.json())
			    .then(room => {
			        // 將房間信息填充到修改房間模態框中
			        $('#editRoomNumber').val(room.room_id);
					 $('#editRoomType').val(room.type_name);

			        // 顯示修改房間模態框
			        $('#editRoomModal').modal('show');
			    })
			    .catch(error => {
			        console.error('獲取房間信息失敗:', error);
			        Swal.fire({
		                    icon: 'error',
		                    title: '無法取得房間資料!。',
		                    confirmButtonText: 'OK'
		                });
			        
			    });
		});
	 	
	 	$('#editRoomButton').on('click', function(e) {
			// 獲取輸入的房間編號和房型
		    var roomNumber = document.getElementById('editRoomNumber').value;
		    var roomType = document.getElementById('editRoomType').value;
			console.log("roomNumber"+roomNumber);
			console.log("roomType"+roomType);
		    // 構建要發送的資料物件
		    var formData = {
		        roomNumber: roomNumber,
		        roomType: roomType
		    };

		    // 發送 POST 請求
        	fetch('/api/updateRoom', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData) // 將資料物件轉為 JSON 字串
            })
            .then(response => {
                if (response.ok) {
                    // 如果响應狀態碼為 200-299，解析 JSON 數據
                    return response.text(); // 返回文本數據
                } else {
                    // 如果响應狀態碼不在 200-299 範圍內，拋出錯誤
                    throw new Error('修改房間失敗');
                }
            })
            .then(message => {
                // 根據後端返回的訊息來判斷成功或失敗
                if (message.startsWith('修改房間成功')) {
                    console.log('修改房間成功:', message);
                    Swal.fire({
			             icon: 'success',
			             title: '修改房間成功！',
			             showConfirmButton: false,
		                 timer: 1000
			        })
                    $('#editRoomModal').modal('hide'); // 關閉模態框
                    fetchRooms(); // 重新加載房間資料
                } else {
                    throw new Error('修改房間失敗');
                }
            })
            .catch(error => {
                // 處理錯誤情況
                console.error('修改房間失敗:', error);
                Swal.fire({
		                    icon: 'error',
		                    title: '修改房間失敗！請檢查!',
		                    confirmButtonText: 'OK'
		                });
                
            });
			
	 	});
	 
	 //--新增房型--------------------------------------------------------------
		 $('#addroomtypeButton').on('click', function(e) {
		    e.preventDefault();
		
		    // 获取输入的房型名稱、單晚價格、容納人數和圖片
		    var roomTypeName = document.getElementById('inputRoomTypeName').value;
		    var roomTypePrice = document.getElementById('inputRoomTypePrice').value;
		    var roomTypeCapacity = document.getElementById('inputRoomTypeCapacity').value;
		    var roomTypeImage = document.getElementById('inputRoomTypeImage').value;
		
		    // 构建要发送的数据对象
		    var formData = {
		        roomTypeName: roomTypeName,
		        roomTypePrice: roomTypePrice,
		        roomTypeCapacity: roomTypeCapacity,
		        roomTypeImage: roomTypeImage
		    };
		
		    // 发送 POST 请求
		    fetch('/api/addroomtype', {
		        method: 'POST',
		        headers: {
		            'Content-Type': 'application/json'
		        },
		        body: JSON.stringify(formData) // 将数据对象转为 JSON 字符串
		    })
		    .then(response => {
		        if (response.ok) {
		            // 如果响应状态码为 200-299，解析 JSON 数据
		            return response.text(); // 返回文本数据
		        } else {
		            // 如果响应状态码不在 200-299 范围内，抛出错误
		            throw new Error('新增房型失败');
		        }
		    })
		    .then(message => {
		        // 根据后端返回的消息来判断成功或失败
		        if (message.startsWith('新增房型成功')) {
		            console.log('新增房型成功:', message);
		            Swal.fire({
			             icon: 'success',
			             title: '新增房型成功！',
			             showConfirmButton: false,
		                 timer: 1000
			        })
		            // 清空表单
		            document.getElementById('inputRoomTypeName').value = '';
		            document.getElementById('inputRoomTypePrice').value = '';
		            document.getElementById('inputRoomTypeCapacity').value = '';
		            document.getElementById('inputRoomTypeImage').value = '';
		            // 关闭模态框
		            $('#addRoomTypeModal').modal('hide');
		             fetchRoomtypes();
		            
		        } else {
		            throw new Error('新增房型失败');
		        }
		    })
		    .catch(error => {
		        // 处理错误情况
		        console.error('新增房型失败:', error);
		        Swal.fire({
		                    icon: 'error',
		                    title: '新增房型失敗！請檢查!',
		                    confirmButtonText: 'OK'
		                });

		    });
		});
	 //--刪除房型------------------------------------------------------------
		// 監聽刪除按鈕的點擊事件（事件委派）
		$('#room-type-management tbody').on('click', '.delete-btn', function() {
		    var roomTypeId = $(this).data('roomtype-id');
		    console.log('Delete room type with ID:', roomTypeId);
		
		    // 使用 SweetAlert 彈窗代替 confirm
		    Swal.fire({
		        title: '確定要刪除這個房型嗎？',
		        text: '請確認此房型並無房間！',
		        icon: 'warning',
		        showCancelButton: true,
		        confirmButtonText: '確定',
		        cancelButtonText: '取消'
		    }).then((result) => {
		        if (result.isConfirmed) {
		            // 如果用戶確定刪除
		            fetch('/api/deleteRoomType/' + roomTypeId, {
		                method: 'DELETE',
		            })
		            .then(response => {
		                if (response.ok) {
		                    console.log('房型成功刪除');
		                    Swal.fire({
		                        icon: 'success',
		                        title: '刪除房型成功！',
		                        showConfirmButton: false,
		                        timer: 1500 // 1.5秒后自动关闭弹窗
		                    });
		                    fetchRoomtypes(); // 刷新房型列表
		                } else {
		                    throw new Error('刪除房型失敗');
		                }
		            })
		            .catch(error => {
		                console.error('刪除房型時發生錯誤:', error);
		                // SweetAlert 顯示錯誤消息
		                Swal.fire({
		                    icon: 'error',
		                    title: '刪除房型失敗！請檢查!',
		                    confirmButtonText: 'OK'
		                });
		            });
		        }
		    });
		});

	//--修改房型------------------------------------------------------------
	
		// 監聽修改按鈕的點擊事件（事件委派）
		$('#room-type-management tbody').on('click', '.update-btn', function() {
		    var roomTypeId = $(this).data('roomtype-id'); // 從按鈕的 data-roomtype-id 獲取房型ID
		    // 根據房型ID獲取房型信息
		    fetch('/api/roomtype/' + roomTypeId)
		        .then(response => response.json())
		        .then(roomtype => {
		            // 將房型信息填充到修改房型模態框中
		             $('#editRoomTypeId').val(roomTypeId);
		            $('#editRoomTypeName').val(roomtype.name);
		            $('#editRoomTypePrice').val(roomtype.price);
		            $('#editRoomTypeCapacity').val(roomtype.capacity);
		            $('#editRoomTypeImage').val(roomtype.photo);
		
		            // 顯示修改房型模態框
		            $('#editRoomTypeModal').modal('show');
		        })
		        .catch(error => {
		            console.error('獲取房型信息失敗:', error);
		             Swal.fire({
		                    icon: 'error',
		                    title: '無法取得房型資料！請檢查!',
		                    confirmButtonText: 'OK'
		                });
		        
		        });
		});
		
		
		$('#editRoomTypeButton').on('click', function(e) {
		    // 獲取輸入的房型名稱、價格、容納人數和圖片
		    var roomTypeId = document.getElementById('editRoomTypeId').value;
		    var roomTypeName = document.getElementById('editRoomTypeName').value;
		    var roomTypePrice = document.getElementById('editRoomTypePrice').value;
		    var roomTypeCapacity = document.getElementById('editRoomTypeCapacity').value;
		    var roomTypeImage = document.getElementById('editRoomTypeImage').value;
		    
		  
		
		    // 構建要發送的資料物件
		    var formData = {
		        roomTypeId: roomTypeId,
		        roomTypeName: roomTypeName,
		        roomTypePrice: roomTypePrice,
		        roomTypeCapacity: roomTypeCapacity,
		        roomTypeImage: roomTypeImage
		    };
		
		    // 發送 PUT 請求
		    fetch('/api/updateRoomType', {
		        method: 'PUT',
		        headers: {
		            'Content-Type': 'application/json'
		        },
		        body: JSON.stringify(formData) // 將資料物件轉為 JSON 字串
		    })
		    .then(response => {
		        if (response.ok) {
		            // 如果响應狀態碼為 200-299，解析 JSON 數據
		            return response.text(); // 返回文本數據
		        } else {
		            // 如果响應狀態碼不在 200-299 範圍內，拋出錯誤
		            throw new Error('修改房型失敗');
		        }
		    })
		    .then(message => {
		        // 根據後端返回的訊息來判斷成功或失敗
		        if (message.startsWith('修改房型成功')) {
		            console.log('修改房型成功:', message);
		            Swal.fire({
			             icon: 'success',
			             title: '修改房型成功！',
			             showConfirmButton: false,
		                 timer: 1000
			        })
		            $('#editRoomTypeModal').modal('hide'); // 關閉模態框
		            fetchRoomtypes(); // 重新加載房型資料
		        } else {
		            throw new Error('修改房型失敗');
		        }
		    })
		    .catch(error => {
		        // 處理錯誤情況
		        console.error('修改房型失敗:', error);
		         Swal.fire({
		                    icon: 'error',
		                    title: '修改房型失敗！請檢查!',
		                    confirmButtonText: 'OK'
		                });
		        
		    });
		});
});