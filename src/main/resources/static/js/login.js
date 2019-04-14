var carousel=new Vue({
    el:"#loginBackground",
    data:{
        list:["slideImg0","slideImg1","slideImg2"],
        viewHeight:"300px"
    },

    mounted(){
        const that=this;
        this.viewHeight=document.body.clientHeight.toString()+"px";
        window.addEventListener("resize",function () {
            that.viewHeight=document.body.clientHeight.toString()+"px";
        },false)
    }

})