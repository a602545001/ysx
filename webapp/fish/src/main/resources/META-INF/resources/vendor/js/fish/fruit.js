var fruitObj = function(){
  this.alive=[];
  this.x=[];
  this.y=[];
  this.aneNo=[];
  this.l=[];
  this.stp=[];
  this.fruitType=[];
  this.orange = new Image();
  this.blue = new Image();
}
fruitObj.prototype.num=30;
fruitObj.prototype.init=function(){
  for(var i=0;i<this.num;i++){
    this.alive[i]=false;
    this.x[i]=0;
    this.y[i]=0;
    this.aneNo[i]=0;
    this.stp[i]=Math.random()*0.017+0.003;
    this.born(i);
  }
  this.orange.src="/vendor/images/fish/fruit.png";
  this.blue.src="/vendor/images/fish/blue.png";
}
fruitObj.prototype.draw=function(){
  for(var i=0;i<this.num;i++){
    if(this.alive[i]){
      var pic = this.fruitType[i] == "blue"?this.blue:this.orange;
      if(this.l[i]<=14){
        var NO=this.aneNo[i];
        this.x[i]=ane.headx[NO];
        this.y[i]=ane.heady[NO]
        this.l[i] += this.stp[i]*deltaTime;
      }else{
        this.y[i] -= this.stp[i]*7*deltaTime;
      }
      ctx2.drawImage(pic, this.x[i]-this.l[i]*0.5,
        this.y[i]-this.l[i]*0.5,this.l[i],this.l[i]);
      if(this.y[i]<10){
        this.alive[i]=false;
      }
    }
  }
}

fruitObj.prototype.born=function(i){
  this.aneNo[i]=Math.floor(Math.random()*ane.num);
  this.l[i]=0;
  var ran = Math.random();
  this.fruitType[i]=ran<0.2?"blue":"orange";
  this.alive[i]=true;
}
fruitObj.prototype.dead=function(i){
  this.alive[i]=false;
}
function fruitMonitor(){
  var num=0;
  for(var i=0; i<fruit.num;i++){
    if(fruit.alive[i]){
      num++;
      sendFruit();
    }
  }
  if(num<15){
    return;
  }
}

function sendFruit(){
  for(var i=0; i<fruit.num;i++){
    if(!fruit.alive[i]){
        fruit.born(i);
        return;
    }
  }
}
