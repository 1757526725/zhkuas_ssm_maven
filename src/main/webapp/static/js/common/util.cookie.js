/**
 * @brief   Javascript cookie packaging operation
 * @author  hechangmin@gmail.com
 * @date	2010.5
 * @version 1.1
 */

//if(typeof Util === "undefined"){ Util = {};}
window.Util = window.Util || {};

Util.Cookie = {

    /**
     * @brief  获取cookie
     * @param {String} key
     * @return{String} value
     */
    get: function(name) {
        var tmp, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)", "gi");
        if ((tmp = reg.exec(unescape(document.cookie))))
            return (tmp[2]);
        return null;
    },

    /**
     * @brief 设置 cookie
     * @param {String} key (name)
     * @param {String} value
     * @param {number} expires 有效期 0 永不过期， 单位为分钟
     * @param {String} path
     * @param {String} domain
     */
    set: function(name, value, expires, path, domain) {
        var str = name + "=" + escape(value);

        if (expires !== null) {
            if (expires == 0) {
                expires = 100 * 365 * 24 * 60;
            }
            var exp = new Date();
            exp.setTime(exp.getTime() + expires * 60 * 1000);
            str += "; expires=" + exp.toGMTString();
        }
        if (path) {
            str += "; path=";
            str += path;
        }
        if (domain) {
            str += "; domain=";
            str += domain;
        }
        document.cookie = str;
    },

    /**
     * @brief  删除 cookie
     * @param {String} name
     * @param {String} path
     * @param {String} domain
     * @remark 原理：将有效期设置为过去
     */
    del: function(name, path, domain) {
        var strCookie = name;
        strCookie += "=";
        strCookie += ((path) ? "; path=" + path : "");
        strCookie += ((domain) ? "; domain=" + domain : "");
        strCookie += "; expires=Thu, 01-Jan-70 00:00:01 GMT";

        document.cookie = strCookie;
    },

    /**
     * @brief  清除所有 cookie
     * @param {String} path
     * @param {String} domain
     */
    clear: function(path, domain){
        var strCookie;
        var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
        for(var i = 0, l = keys.length; i < l; ++i){
            Util.Cookie.del(keys[i], path, domain);
        }
    }
};