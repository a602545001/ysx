var can1;
var can2;

var ctx1;
var ctx2;

var canwidth;
var canHeight;

var mx;
var my;

var ane;
var fruit;

var mom;
var baby;

var data;

var babyTail=[];
var babyEye=[];
var babyFade=[];

var bigTail=[];
var bigEye=[];
var bigBody=[];
var bigBodyBlue=[];

var bgPic=new Image();
document.body.onload=game;
//$(window).resize(init);
function game(){
  init();
  lastTime = Date.now();
  deltaTime = 0;
  gameloop();
}
function init(){
  can1 = document.getElementById('canvas1');
//  var h = $(this).height();
//  var w = $(this).width();
//  can1.setAttribute('width', w);
//  can1.setAttribute('height', h);
  ctx1 = can1.getContext('2d');
  
  can2 = document.getElementById('canvas2');
//  can2.setAttribute('width', w);
//  can2.setAttribute('height', h);
  ctx2 = can2.getContext('2d');

//  $("#allcanvas").css("width",w);
//  $("#allcanvas").css("height",h);
  
  can1.addEventListener("mousemove", onMouseMove, false);

  bgPic.src = "/vendor/images/fish/background.jpg";
  canwidth = can1.width;
  canHeight = can1.height;

  ane = new aneObj();
  ane.init();

  fruit = new fruitObj();
  fruit.init();

  mom = new momObj();
  mom.init();

  baby = new babyObj();
  baby.init();

  data = new dataObj();


  mx = canwidth*0.5;
  my = canHeight*0.5;

  for(var i=0;i<8;i++){
    babyTail[i]=new Image();
    babyTail[i].src="/vendor/images/fish/babyTail"+i+".png";
  }
  for(var i=0;i<2;i++){
    babyEye[i]=new Image();
    babyEye[i].src="/vendor/images/fish/babyEye"+i+".png";
  }
  for(var i=0;i<20;i++){
    babyFade[i]=new Image();
    babyFade[i].src="/vendor/images/fish/babyFade"+i+".png";
  }

  for(var i=0;i<8;i++){
    bigTail[i]=new Image();
    bigTail[i].src="/vendor/images/fish/bigTail"+i+".png";
  }
  for(var i=0;i<2;i++){
    bigEye[i]=new Image();
    bigEye[i].src="/vendor/images/fish/bigEye"+i+".png";
  }
  for(var i=0;i<8;i++){
    bigBody[i]=new Image();
    bigBody[i].src="/vendor/images/fish/bigSwim"+i+".png";
    bigBodyBlue[i]=new Image();
    bigBodyBlue[i].src="/vendor/images/fish/bigSwimBlue"+i+".png";
  }
  ctx1.textAlign="center";
}
function gameloop(){
  window.requestAnimFrame(gameloop);
  var now = Date.now();
  deltaTime = now-lastTime;
  lastTime = now;
  deltaTime=deltaTime>40?40:deltaTime;
  // console.log(deltaTime);
  // ctx2.drawImage(bgPic, 10, 10, canwidth, canHeight);
  drawBackground();
  ane.draw();

  fruitMonitor();
  fruit.draw();

  ctx1.clearRect(0,0,canwidth,canHeight);
  mom.draw();
  baby.draw();
  data.draw();

  momFruitsCllision();
  momBabyCollision();
}
function onMouseMove(e){
  if(!data.gameOver){
    if(e.offSetX||e.layerX){
      mx=e.offSetX == undefined?e.layerX:e.offSetX;
      my=e.offSetY == undefined?e.layerY:e.offSetY;
    }
  }else{
	  $(".start_bg").css("display","block");
  }
}
