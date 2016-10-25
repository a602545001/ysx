var momObj = function(){
  this.x;
  this.y;
  this.angle;
}
momObj.prototype.init = function(){
  this.x= canwidth*0.5;
  this.y= canHeight*0.5;
  this.angle=0;

  this.bigTailTimer=0;
  this.bigTailCount=0;

  this.bigEyeTimer=0;
  this.bigEyeCount=0;
  this.bigEyeInterval=1000;

  this.bigCount=0;
}
momObj.prototype.draw = function(){
  this.x=lerpDistance(mx,this.x,0.98);
  this.y=lerpDistance(my,this.y,0.98);

  var deltaY = my-this.y;
  var deltaX = mx-this.x;
  var beta=Math.atan2(deltaY,deltaX)+Math.PI
  this.angle=lerpAngle(beta, this.angle, 0.6);

  //mom尾巴
  this.bigTailTimer+=deltaTime;
  if(this.bigTailTimer>50){
    this.bigTailCount=(this.bigTailCount+1)%8;
    this.bigTailTimer%=50;
  }
  //mom眼睛
  this.bigEyeTimer+=deltaTime;
  if(this.bigEyeTimer>this.bigEyeInterval){
    this.bigEyeCount=(this.bigEyeCount+1)%2;
    this.bigEyeTimer%=this.bigEyeInterval;
    if(this.bigEyeCount==0){
      this.bigEyeInterval=Math.random()*1500+2000;
    }else{
        this.bigEyeInterval=200;
    }
  }

  ctx1.save();
  ctx1.translate(this.x,this.y);
  ctx1.rotate(this.angle);
  ctx1.drawImage(bigTail[this.bigTailCount],-bigTail[this.bigTailCount].width*0.5+31,-bigTail[this.bigTailCount].height*0.5);
  if(data.double==1){
    ctx1.drawImage(bigBody[this.bigCount],-bigBody[this.bigCount].width*0.5,-bigBody[this.bigCount].height*0.5);
  }else{
    ctx1.drawImage(bigBodyBlue[this.bigCount],-bigBodyBlue[this.bigCount].width*0.5,-bigBodyBlue[this.bigCount].height*0.5);
  }
  ctx1.drawImage(bigEye[this.bigEyeCount],-bigEye[this.bigEyeCount].width*0.5,-bigEye[this.bigEyeCount].height*0.5);
  ctx1.restore();
}
