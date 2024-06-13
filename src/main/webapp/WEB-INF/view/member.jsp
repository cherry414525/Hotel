<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">

  <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>會員中心</title>
    <link rel="icon" href="../hotel_img/icon-hotel.webp">
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
      }

      .wrapper {
        min-height: 80vh;
        display: flex;
        flex-direction: column;
      }

      /* 調整主要內容區域 */
      .main-content {
        flex: 1;
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
              <h1> ABC Hotel</h1>
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
                  <a class="nav-link" href="#">會員中心</a>
                </li>
                
                <li class="nav-item">
                  <a class="nav-link" href="/logout">登出</a>
                </li>
              </ul>
            </div>
          </div>
        </nav>


      </header>
      <!-- Main Content Area -->
      <main class="main-content mb-3" style="margin-top: 120px;">
        <div class="container mt-1">
          <!-- 電腦版訂單列表 -->
          <div class="d-none d-md-block">
            <h2 class="mb-3">訂單列表</h2>
            <table class="table">
            <input name="_method" id="_method" type="hidden" value=${method}/>
            
              <thead>
                <tr>
                  <th>訂單編號</th>
                  <th>房型</th>
                  <th>入住時間</th>
                  <th>退房時間</th>   
                  <th>金額</th>
                  <th>修改</th>
                  <th>刪除</th>
                </tr>
              </thead>
              <tbody>
              	<c:forEach items="${ bookingRoomDtos }" var="bookingRoomDtos">
                <tr>
                  <td>${bookingRoomDtos.bookingId}</td>
                  <td>${bookingRoomDtos.roomType.name}</td>
                  <td>${bookingRoomDtos.start_date}</td>
                  <td>${bookingRoomDtos.end_date}</td>
                  <td>${bookingRoomDtos.price }</td>
                  <td><a href="booking.jsp" class="btn btn-primary manage-btn">修改</a></td>
    			  <td>
	    			  <form action="/member/delete/${bookingRoomDtos.bookingId}" method="POST" style="display: inline;">
	    			  		<input name="_method" id="_method" type="hidden" value="DELETE" />
					  		<button type="submit" class="btn btn-danger pure-button" >刪除</button>
	    			  </form>
    			  </td>
                  
                </tr>
                </c:forEach>
              </tbody>
            </table>
            <!-- Pagination -->
			<nav aria-label="Page navigation example">
			  <ul class="pagination justify-content-center">
			    <li class="page-item ${currentPage == 0 ? 'disabled' : ''}">
			      <a class="page-link" href="?page=${currentPage - 1}" tabindex="-1" aria-disabled="${currentPage == 0}">Previous</a>
			    </li>
			    <!-- Bootstrap 分頁組件 -->
			    <c:forEach var="pageNumber" begin="0" end="${totalPages - 1}">
			      <li class="page-item ${pageNumber == currentPage ? 'active' : ''}">
			        <a class="page-link" href="?page=${pageNumber}">${pageNumber + 1}</a>
			      </li>
			    </c:forEach>
			    <li class="page-item ${currentPage == totalPages - 1 ? 'disabled' : ''}">
			      <a class="page-link" href="?page=${currentPage + 1}">Next</a>
			    </li>
			  </ul>
			</nav>
          </div>

          <!-- 手機版訂單列表 -->
          <div class="d-md-none">
            <h2 class="mb-3">訂單列表</h2>
            <!-- Accordion -->
            <div id="accordion">
            <c:forEach items="${ bookingRoomDtos }" var="bookingRoomDtos">
              <div class="card">
                <div class="card-header" id="headingOne">
                  <h5 class="mb-0">
                    <button class="btn btn-link" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true"
                      aria-controls="collapseOne">
                      訂單編號：${bookingRoomDtos.bookingId} - 房型：${bookingRoomDtos.roomType.name}
                    </button>
                  </h5>
                </div>
                <div id="collapseOne" class="collapse show" aria-labelledby="headingOne" data-parent="#accordion">
                  <div class="card-body">
                    <p>入住時間：${bookingRoomDtos.start_date}</p>
                    <p>退房時間：${bookingRoomDtos.end_date}</p>
                    <p>金額：${bookingRoomDtos.price }</p>
                    <a href="booking.jsp" class="btn btn-primary manage-btn">修改</a>
	    			  <form action="/member/delete/${bookingRoomDtos.bookingId}" method="POST" style="display: inline;">
	    			  		<input name="_method" id="_method" type="hidden" value="DELETE" />
					  		<button type="submit" class="btn btn-danger pure-button" >刪除</button>
	    			  </form>
                  </div>
                </div>
              </div>
              </c:forEach>
            </div>
          </div>
        </div>
    </div>

    </main>
    <footer class="bg-light">
      <div class="container ">
        <h3>ABC Hotel</h3>
        <hr>
        <p>
        <div><img src="../hotel_img/icon-map.webp" style="width: 1em; height: 1em;"> 地址: 10491台北市中山區民生東路三段
        </div>
        <p>
        <div><img src="../hotel_img/icon-mail.webp" style="width: 1em; height: 1em;"> Email:
          abcdefg1234@gmail.com</div>
        <p>
        <div><img src="../hotel_img/icon-phone.webp" style="width: 1em; height: 1em;"> 電話: 0987654321</div>
        <p>
      </div>
    </footer>
    </div>

    <script>

      $(document).ready(function () {
        $('.navbar-nav>li>a').on('click', function () {
          $('.navbar-collapse').collapse('hide');
        });
      });

      
    </script>

  </body>

</html>