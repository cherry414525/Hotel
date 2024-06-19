<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>會員中心</title>
  <link rel="icon" href="http://localhost:8086/hotel_img/icon-hotel.webp">
  <!-- Bootstrap CSS -->
  <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
  <!-- Bootstrap JS 和 jQuery -->
  <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
  <!-- Custom CSS -->
  <style>
    * {
      font-family: Georgia, serif;
    }

    /* navbar*/
    nav.navbar {

      /* 陰影效果 */
      box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
      /* 確保 nav 區塊在最上層 */
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      z-index: 1000;

      font-weight: bold;
    }

    /* main */
    body {

      /*background-color: #FFFEFB;*/
      font-family: Arial, sans-serif;
       min-height: 100vh;
	  display: flex;
	  flex-direction: column;
    }

    .wrapper {
      min-height: 100vh;
      display: flex;
      flex-direction: column;
      flex: 1;
    }

    /* 調整主要內容區域 */
    .main-content {
      flex: 1;
    }
    h1 {
                font-weight: bolder;
                font-feature-settings: "smcp", "zero";
            }
  </style>
</head>

<body>
  <div class="wrapper">
    <header>
      <!-- Navigation -->
      <nav class="navbar navbar-expand-lg  navbar-light bg-light">
        <div class="container">
          <a class="navbar-brand" href="#">
            <h1> Starry Hotel</h1>
          </a>
          <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>

          <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ml-auto">
              <li class="nav-item">
                <a class="nav-link" href="/hotel">首頁</a>
              </li>

              <li class="nav-item">
                <a class="nav-link" href="/member">會員中心</a>
              </li>
            </ul>
          </div>
        </div>
      </nav>


    </header>
    <!-- Main Content Area -->
    <!-- Main Content Area -->
    <main class="main-content align-content-center" style="margin: 85px;">
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-md-6 ">
            <div id="bookingForm">
              <h2 class="text-center">訂房資訊</h2>
              <!-- 表單開始 -->
              <form method="POST" action="/booking/addbooking">
                <input type="hidden" name="_method" value="POST">
                <div class="form-group">
                  <label for="username">會員姓名</label>
                  <input type="text" class="form-control" id="name" name="name" value="${loggedInUser.name}" readonly>
                </div>
                <div class="form-group">
                  <label for="roomType">房型</label>
                  <input type="text" class="form-control" id="roomType" name="roomType" value="${roomtype}" readonly>
                </div>
                <div class="form-group">
                  <label for="start_date">入住時間</label>
                  <input type="date" class="form-control"  id="start_date" name="start_date" value="${start_date}" onchange="calculateDate()">
                </div>
                <div class="form-group">
                  <label for="end_date">退房時間</label>
                  <input type="date" class="form-control" id="end_date" name="end_date" value="${end_date}" onchange="calculateDate()">
                </div>
               <!-- <div class="form-group">
                  <label for="quantity">數量</label>
                  <input type="number" class="form-control" id="quantity" name="quantity" value="${quantity}" readonly>
                </div>-->
                <div class="form-group">
                  <label for="price">金額</label>
                  <input type="text" class="form-control" id="price" name="price" value="${totalPrice}" readonly>
                </div>
                <!-- 將按鈕置於同一行的不同列中 -->
                <div class="row justify-content-end">

                  <!-- 修改按鈕 -->
                  <div class="col-auto">
                    <button type="submit" id="submitBtn" class="btn btn-primary">確認訂房</button>
                  </div>
                  <!-- 回上頁按鈕 -->
                  <div class="col-auto">
                    <a href="/hotel"  id="cancelBtn" class="btn btn-secondary">取消</a>
                  </div>
                </div>
              </form>

            </div>


          </div>
        </div>
      </div>

    </main>



    <footer class="bg-light">
      <div class="container ">
        <h3>Starry Hotel</h3>
        <hr>
        <p>
          <div><img src="http://localhost:8086/hotel_img/icon-map.webp" style="width: 1em; height: 1em;"> 地址: 10491台北市中山區民生東路三段
          </div>
        <p>
          <div><img src="http://localhost:8086//hotel_img/icon-mail.webp" style="width: 1em; height: 1em;"> Email:
            Starry1234@gmail.com</div>
        <p>
          <div><img src="http://localhost:8086/hotel_img/icon-phone.webp" style="width: 1em; height: 1em;"> 電話: 0987654321</div>
        <p>
      </div>
    </footer>
  </div>



  <script>
		  
  	var basePath = '../hotel_img/';
    $(document).ready(function () {
      $('.navbar-nav>li>a').on('click', function () {
        $('.navbar-collapse').collapse('hide');
      });
      
      calculateDate();
      
      
      // 如果是修改操作，設置表單的 action 為更新路徑
        if (${update}) {
            $('form').attr('action', '/booking/updatebooking/${bookingId}');
            var methodInput = document.querySelector('input[name="_method"]');
            methodInput.value = 'PUT';
        }
    });

 	// 如果是新增操作，将输入框设置为只读
    if (!${update}) {
    	start_date.readOnly = true;
    	end_date.readOnly = true;
    	$('#submitBtn').text('確認訂房');
    	 
    	 $('#cancelBtn').attr('href', '/hotel');

    } else {
        // 如果是修改操作，保持输入框为可编辑状态
        var stayDuration; // 全局變數
		  // 計算日期天數的函式
		  function calculateDate() {
		              var checkInDate = new Date(document.getElementById('start_date').value);
		              var checkOutDate = new Date(document.getElementById('end_date').value);
		
		              // 計算時間差，以天數為單位
		              var timeDiff = Math.abs(checkOutDate.getTime() - checkInDate.getTime());
		              stayDuration = Math.ceil(timeDiff / (1000 * 3600 * 24));
		
		              // 在控制台打印入住到退房的天數
		              console.log("入住到退房的天數: " + stayDuration);
				
		              var price = parseFloat(${roomtypePrice});
		              console.log("price: " + price);
                      var totalPrice = price * stayDuration;
					  console.log("totalPrice: " + totalPrice);
					  $('#price').val(totalPrice.toFixed(2));
              
          }
        start_date.readOnly = false;
        end_date.readOnly = false;
        
        $('#submitBtn').text('確認修改');
        $('#cancelBtn').attr('href', '/member');
    }
  </script>

</body>

</html>