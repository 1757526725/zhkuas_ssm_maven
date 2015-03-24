var EventUtil = {
    getEvent: function (e) {
        return e || window.event;
    },
 
    getTarget: function (e) {
        return e.target || e.srcElement;
    },
 
    preventDefault: function (e) {
        if (e && e.preventDefault) {
            e.preventDefault();
        } else {
            window.event.returnValue = false;
        }
    },
 
    stopPropagation: function (e) {
        if (e && e.stopPropagation) {
            e.stopPropagation();
        } else {
            e.cancelBubble = true;
        }
    },
 
    addHandler: function (ele, evType, fn, useCapture) {
        // 默认使用事件冒泡
        useCapture = useCapture || false;
 
        if (ele.addEventListener) {
            ele.addEventListener(evType, fn, useCapture);
        } else if (ele.attachEvent) {
            ele.attachEvent("on" + evType, function () {
 
                // fn中的this指向ele对象:ie的问题
                fn.call(ele);  
            });
        } else {
            ele["on" + evType] = fn;
        }
    },
 
    removeHandler: function (ele, evType, fn) {
        if (ele.removeEventListener) {
            ele.removeEventListener(evType, fn);
        } else if (ele.detachEvent) {
            ele.detachEvent("on" + evType, fn);
        } else {
            ele["on" + evType] = null;
        }
    }
};