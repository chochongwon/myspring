<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile Test</title>
         
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
         
        <style>
            #currentProfile{
                width:250px; height:50px; line-height:50px;
                position: absolute; top: 1px; right: 1px;
                border: 1px solid #d4c98a; background-color: #fff8d2;
                text-align:center; vertical-align: middle;
                font-size: 18pt;
                cursor: pointer;
            }
        </style>
    </head>
     
    <body>
        <div id="currentProfile">${ currentProfile } Mode</div>    
         
        <p>mainBean - ${ mainBean }</p>
        <p>subBean - ${ subBean }</p>
         
        <script>
            $("#currentProfile").on("click", function(){
                window.location.href = "/sample/toggleProfile";
            });
        </script>
    </body>
</html>