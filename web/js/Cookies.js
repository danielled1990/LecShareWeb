

function getCookie(cookieName){
    var name = cookieName + "=";
    var cookies = document.cookie.split(';');

    for(var i = 0; i < cookies.length; ++i){
        var cookie = cookies[i];

        while(cookie.charAt(0) == ' '){
            cookie = cookie.substring(1);
        }

        if(cookie.indexOf(name) == 0){
            return cookie.substring(name.length, cookie.length);
        }
    }

    return "";
}

function setCookie(cookieName, cookieValue){
    var date = new Date();
    //Expiration in 30 days
    date.setTime(date.getTime() + 30 * (24*60*60*1000));
    var expires = "expires="+date.toUTCString();

    document.cookie = cookieName + "=" + cookieValue + "; path=/;" + expires; 
}

function setSessionCookie(cookieName, cookieValue){
    var date = new Date();
    //Expiration in 30 days
    date.setTime(-1);
    var expires = "expires="+date.toUTCString();

    document.cookie = cookieName + "=" + cookieValue + "; path=/;" + expires; 
}

function deleteCookie(cookieName){
    setCookie(cookieName, "", -1)
}
    