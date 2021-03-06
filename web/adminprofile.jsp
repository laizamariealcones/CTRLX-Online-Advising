<!DOCTYPE html>
<html>

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Profile | UST Online Advising System</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap-theme.min.css">
        <link rel="stylesheet" href="assets/css/styles.min.css">
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    </head>

    <body>
        <header class="black-border">
            <div><img class="img-responsive" src="assets/img/header.png" style="max-width:60%;height:auto;margin-top:0px;padding:9px;margin-bottom:0px;max-height:100%;"></div>
        </header>

        <!-- NAVBAR -->

        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <div class="navbar-header"><button class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button></div>
                <div
                    class="collapse navbar-collapse" id="navcol-1">
                    <form action="AdminServlet" method="get">
                        <ul class="nav navbar-nav">
                            <li role="presentation"><button class="formBTN" type="submit" name="myProfile">MyProfile</button></li>
                            <li role="presentation"><button class="formBTN" type="submit" name="manage">Manage</button></li>
                        </ul>
                        <ul class="nav navbar-nav navbar-right">
                            <li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false" href="#" style="color: white;">Account <span class="caret"></span></a>
                                <ul class="dropdown-menu" role="menu">
                                    <li role="presentation"><button class="navbar-items" type="submit" name="changePassword"/>Change Password</button></li>
                                </ul>
                          <li role="presentation"><a style="color: black;" href="adminfacultyindex.jsp">Logout</a></li>
                        </ul>
                    </form>

                </div>
            </div>
        </nav>

        <!-- PROFILE -->

        <section style="padding:0px;">
            <div class="container-fluid" style="padding-right:90px;padding-left:90px;">
                <div class="row">
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                        <h3 class="text-nowrap" style="color:#ffcd48;">Administrator Profile</h3>
                    </div>
                    <div class="col-lg-2 col-md-2 col-sm-12 col-xs-12"><img class="img-responsive" src="assets/img/Angelo.jpg" style="height:145px;max-height:100%;"></div>
                    <div class="col-lg-10 col-md-10 col-sm-12 col-xs-12">
                        <h5 class="header-prof-yellow" style="margin:0px;">Personal Information</h5>
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th style="width:251px;">Employee Number</th>
                                        <th>${Employee_ID} 123456789 </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td style="width:250px;">Last Name</td>
                                        <td>${Last_Name} Dela Cruz </td>
                                    </tr>
                                    <tr>
                                        <td>First Name</td>
                                        <td>${First_Name} Juan </td>
                                    </tr>
                                    <tr>
                                        <td>Middle Name</td>
                                        <td>${Middle_Name} Cruz </td>
                                    </tr>
                                    <tr>
                                        <td>Birth Date</td>
                                        <td>${Birthdate} 01/01/1969 </td>
                                    </tr>
                                    <tr>
                                        <td>Email Address</td>
                                        <td>${Email} juandelacruz@gmail.com  </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <div></div>
                        <h5 class="header-prof-yellow" style="margin:0px;">Faculty Information</h5>
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th style="width:250px;"><strong>Department</strong> </th>
                                        <th>${Department} Electronics Engineering</th>
                                    </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </body>
    <footer style="background-color: #000000; position: absolute; width: 100%; color: white; 
            padding:10px; padding-top: 15px; margin-top: 35px; height: auto; 
            line-height: 10px; bottom: auto; z-index: -99999; padding-right: 0px; padding-left: 0px;">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12s">
                    <center>
                        <p style="line-height: 12px;" class="text-cen">Copyright � 2016  University of Santo Tomas. All rights reserved.</p> 
                        <!-- <p class="text-light">All rights reserved.</p> -->
                        <p class="text-light">Powered by <a href="http://steps.ust.edu.ph/" style="color: goldenrod;" target="_blank">Santo Tomas e-Service Providers</a></p>
                    </center>
                </div>
            </div>
        </div>
    </footer>
</html>