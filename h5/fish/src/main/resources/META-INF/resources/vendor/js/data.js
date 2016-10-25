var dataObj=function(){
  this.fruitNum=0;
  this.double=1;
  this.score=0;
  this.gameOver=false;
  this.alpha=0;
}

dataObj.prototype.draw=function(){
  var w=can1.width;
  var h=can1.height;

  ctx1.save();
  ctx1.fillStyle="white";
  ctx1.font="20px 黑体";
  ctx1.fillText("鱼吃了: "+this.score+" 斤饲料",w*0.5,h-10);
  if(this.gameOver){
    this.alpha+=deltaTime*0.0005;
    this.alpha>1?1:this.alpha;
    ctx1.fillStyle="rgba(255,255,255,"+this.alpha+")";
    ctx1.font="50px 黑体";
    ctx1.shadowBlur=20;
    ctx1.shadowColor="black";
    ctx1.fillText("太菜了!",w*0.5,h*0.5);
  }
  ctx1.restore();
}
dataObj.prototype.addScore=function(){
  this.score+=this.fruitNum*1*this.double;
  this.fruitNum=0;
  this.double=1;
}
