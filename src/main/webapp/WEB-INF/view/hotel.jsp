<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Hotel</title>
        <link rel="icon" href="../hotel_img/icon-hotel.webp"> 
        <!-- Bootstrap CSS -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <!-- Bootstrap JS and jQuery -->
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

            .card {
                /* 陰影效果 */
                box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.5);
            }

            h1 {
                font-weight: bolder;
                font-feature-settings: "smcp", "zero";
            }
        </style>

    </head>

    <body>

        <header>
            <!-- Navigation -->
            <nav class="navbar navbar-expand-lg  navbar-light bg-light">
                <div class="container">
                    <a class="navbar-brand" href="#">
                        <h1> ABC Hotel</h1>
                    </a>
                    <button class="navbar-toggler" type="button" data-toggle="collapse"
                        data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                        aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>

                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav ml-auto">
                            <li class="nav-item">
                                <a class="nav-link" href="#sec1">飯店介紹</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#sec2">房型介紹</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#sec3">設施與服務</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#sec4">交通資訊</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="login.html">會員中心</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>

            <div class="container-fluid " style="padding-top: 85px;">
                <div class=" row no-gutters">
                    <div class="col-md-6 d-none d-md-block">
                        <img src="../hotel_img/header1.jpg" alt="Left Image" class="img-fluid"
                            style="height: 350px; width: 100%;">
                    </div>
                    <div class="col-md-6">
                        <img src="../hotel_img/header2.jpg" alt="Right Image" class="img-fluid"
                            style="height: 350px; width:100%;">
                    </div>
                </div>
            </div>





        </header>
        <!-- Main Content Area -->
        <main>
            <!-- #sec1 =================================================================== -->
            <section id="sec1">
                <!-- 飯店介紹-->
                <div class="container mt-4">
                    <h1>飯店介紹</h1><br>
                    <!--<img src="./hotel_img/images.jpg" style="min-width: 50%; height: auto;">-->
                    <h2>❋歡迎來到 ABC 飯店</h2>
                    <div class="feature">

                        <p>ABC 飯店位於城市中心繁華地帶，交通便捷，距離主要景點和商業區都很近，是商務旅客和觀光客的理想下榻之地。飯店擁有多樣化的客房選擇，從奢華的豪華客房到寬敞的套房，滿足不同客人的需求。
                        </p>
                        <p>無論您是前來商務會議還是度假放鬆，ABC 飯店都能為您提供舒適的住宿環境和專業的服務。我們致力於為每一位賓客打造一個溫馨、舒適的家外之家，讓您在旅途中感受到溫暖和安心。</p>
                    </div>
                    <div class="feature">
                        <h2>❋飯店特色</h2>
                        <ul>
                            <li><strong>優雅設計：</strong> ABC 飯店擁有優雅時尚的室內設計，融合了現代與傳統的元素，營造出舒適而精緻的氛圍。</li>
                            <li><strong>豪華設施：</strong> 飯店提供一系列設施，包括室內恆溫游泳池、健身中心、水療中心和商務中心，滿足各種賓客的需求。</li>
                            <li><strong>美食饗宴：</strong> ABC 飯店擁有多家餐廳和酒吧，供應國際美食和精緻的本地菜餚，讓您品嚐各種美味佳餚。</li>
                            <li><strong>專業服務：</strong> 飯店以其熱情周到的服務而聞名，員工經過專業培訓，隨時為賓客提供幫助，確保每一位賓客都能享受到完美的住宿體驗。</li>
                        </ul>
                    </div>
                    <hr>
                </div>

            </section>

            <!-- #sec2 =================================================================== -->
            <section id="sec2">
                <!-- 房型介紹-->
                <div class="container mt-4">
                    <h1>房型介紹</h1><br>

                    <!-- 搜索区块 -->
                    <div class="row mb-4 justify-content-end">
                        <div class="col-md-4 d-flex align-items-center justify-content-around">
                            <label for="check_in_date">入住日期: </label>
                            <input type="date" id="check_in_date" class="form-control" style="width: 70%;" required>
                        </div>
                        <div class="col-md-4 d-flex align-items-center justify-content-around">
                            <label for="check_out_date">退房日期: </label>
                            <input type="date" id="check_out_date" class="form-control" style="width: 70%;" required>
                        </div>
                        <div class="col-md-2 align-self-end text-right">
                            <button type="button" class="btn btn-primary" style="width: 85%; "> 搜尋</button>
                        </div>
                    </div>

                    <!-- 房型1-->
                    <c:forEach items="${ roomtypeDtos }" var="roomtypeDtos">
                    <div class="row">
                        <!-- First Block -->
                        <div class="col-lg-12 col-md-12 mb-4">
                            <div class="card ">
                                <div class="row no-gutters">
                                    <!-- 左半部分放置图片 -->
                                    <div class="col-md-4" style="padding: 8px;">
                                        <img src="../hotel_img/main1.jpg" class="card-img" alt="Your Image">
                                    </div>
                                    <!-- 右半部分放置信息和按钮 -->
                                    <div class="col-md-8">
                                        <div class="card-body">
                                            <h5 class="card-title">${roomtypeDtos.name}</h5>
                                            <hr>
                                            <p class="card-text">適合人數:${roomtypeDtos.capacity}</p>

                                            <!-- 下面的容器放置输入框和订房按钮 -->
                                            <div class="container-fluid "  style="margin-top: 50px;">
                                                <div class="row justify-content-end">
                                                    <!-- 输入框 -->
                                                    <div class="col-md-4 col-sm-6">
                                                        <div class="input-group">
                                                            <div class="input-group-prepend">
                                                                <span class="input-group-text">間數:</span>
                                                            </div>
                                                            <input type="number" class="form-control" placeholder="選擇間數"
                                                                value="1">
                                                        </div>
                                                    </div>
                                                    <!-- 總金額 -->
                                                    <div class="col-md-3 col-sm-6 mt-2 mt-md-2 text-right" >
                                                        <p class="card-text total-money">總金額: ${roomtypeDtos.price}</p>
                                                    </div>
                                                    <!-- 訂房按钮 -->
                                                    <div class="col-md-4 col-sm-12 mt-2 mt-md-0">
                                                        <a href="booking.html" class="btn btn-primary btn-block float-right">訂房</a>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </div>
                    </div>
                    </c:forEach>
                  
                  </div>
                  
            </section>
            <!-- #sec3 =================================================================== -->
            <section id="sec3">

                <!-- 設施與服務-->
                <div class="container mt-4">


                    <h1>設施與服務</h1><br>

                    <ul class="row" style="list-style: none; padding-left: 0; ">
                        <h3 class="col-md-12  font-weight-bold">公共電腦</h3>
                        <li class="col-md-3 col-sm-6"><strong>✓ 公共區域 WIFI</strong></li>
                        <li class="col-md-3 col-sm-6"><strong>✓ 嬰兒床</strong></li>
                        <li class="col-md-3 col-sm-6"><strong>✓ 熨斗 / 燙衣板</strong></li>
                        <li class="col-md-3 col-sm-6"><strong>✓ 電梯</strong></li>
                        <li class="col-md-3 col-sm-6"><strong>✓ 行李寄放</strong></li>
                        <li class="col-md-3 col-sm-6"><strong>✓ 停車場</strong></li>
                        <li class="col-md-3 col-sm-6"><strong>✓ 嬰兒澡盆</strong></li>
                    </ul>

                    <ul class="row" style="list-style: none; padding-left: 0;">
                        <h3 class="col-md-12  font-weight-bold">✓ 餐飲設施</h3>
                        <li class="col-md-3 col-sm-6"><strong>✓ 咖啡</strong></li>
                        <li class="col-md-3 col-sm-6"><strong>✓ 茶 / 咖啡沖泡設施</strong></li>
                        <li class="col-md-3 col-sm-6"><strong>✓ 公用用餐區</strong></li>
                    </ul>

                    <ul class="row" style="list-style: none; padding-left: 0;">
                        <h3 class="col-md-12  font-weight-bold">安全設施</h3>
                        <li class="col-md-3 col-sm-6"><strong>✓ 監視器</strong></li>
                        <li class="col-md-3 col-sm-6"><strong>✓ 醫藥箱</strong></li>
                        <li class="col-md-3 col-sm-6"><strong>✓ 滅火器</strong></li>
                        <li class="col-md-3 col-sm-6"><strong>✓ 逃生設備</strong></li>
                        <li class="col-md-3 col-sm-6"><strong>✓ 灑水設備</strong></li>
                        <li class="col-md-3 col-sm-6"><strong>✓ 煙霧偵測器</strong></li>
                    </ul>


                    <hr>
                </div>

            </section>
            <!-- #sec4 =================================================================== -->
            <section id="sec4">
                <!--交通資訊-->
                <div class="container mt-4">
                    <h1>交通資訊</h1>
                    <p><img src="../hotel_img/icon-map.webp" style="width: 1em; height: 1em;"> 地址: 10491台北市中山區民生東路三段</p>
                    <iframe
                        src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d903.5695651212153!2d121.54285448877282!3d25.0585562239332!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3442ab78b3e2e48f%3A0xf6809a857286e3ab!2z5ZyL56uL5Y-w5YyX5aSn5a245rCR55Sf5qCh5Y2AIOaVmeWtuOWkp-aokw!5e0!3m2!1szh-TW!2stw!4v1716273691066!5m2!1szh-TW!2stw"
                        width="600" height="450" style="border:0;" allowfullscreen="" loading="lazy"
                        referrerpolicy="no-referrer-when-downgrade"></iframe>
                </div>
                <br>
            </section>
        </main>
        <footer class="bg-light">
            <div class="container ">
                <h3>ABC Hotel</h3>
                <hr>
                <p>
                <div><img src="../hotel_img/icon-map.webp" style="width: 1em; height: 1em;"> 地址: 10491台北市中山區民生東路三段</div>
                <p>
                <div><img src="../hotel_img/icon-mail.webp" style="width: 1em; height: 1em;"> Email:
                    abcdefg1234@gmail.com</div>
                <p>
                <div><img src="../hotel_img/icon-phone.webp" style="width: 1em; height: 1em;"> 電話: 0987654321</div>
                <p>
            </div>
        </footer>


        <script>
            $(document).ready(function () {
                $('.navbar-nav>li>a').on('click', function () {
                    $('.navbar-collapse').collapse('hide');
                });
            });
        </script>
    </body>

</html>