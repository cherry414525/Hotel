// 當文件加載完成後執行渲染操作
$(document).ready(function() {
	//--登入按鈕------------------------------------------------------------
	// 登入按鈕點擊事件
	$('#loginButton').click(function(e) {
		e.preventDefault(); // 防止默認的表單提交行為
		console.log('登入按鈕點擊');

		// 獲取會員編號和密碼值	
		var email = $('#loginEmail').val();
		var password = $('#loginPassword').val();
		
		// 建立要發送的數據對象
		var formData = {
			email: email,
			password: password
		};

		// 設置 Fetch 請求的選項
		var requestOptions = {
			method: 'POST', // 使用 POST 方法
			headers: {
				'Content-Type': 'application/json'
			},
			body: JSON.stringify(formData) // 將數據對象轉為 JSON 字符串
		};

		console.log('發送 Fetch 請求到 /api/login');
		// 發送 Fetch 請求
		    fetch('/backlogin', requestOptions)
		    .then(response => {
				
                return response.text(); // 注意這裡返回的是文本
		        
		    })
		    .then(data=> {
			 	console.log('登入結果:', data); // 輸出成功解析的數據，用於確認數據是否正確獲取

	            if (data === "backroom.html") {
	                alert('登入成功！');
	                // 如果登入成功，進行重定向到 backroom.html
	                window.location.href = 'backroom.html';
	            } else {
	                alert('登入失敗:'+data);
	            }
		    })
		    .catch(error => {
		        // 處理錯誤情況
		        console.error('搜尋失敗:', error);
		        alert('登入失敗，請檢查您的Emial和密碼');
		    });  
		
	});
		


});