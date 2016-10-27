//大鱼吃果实
function momFruitsCllision(){
  if(!data.gameOver){
    for(var i=0;i<fruit.num;i++){
      if(fruit.alive[i]){
        var l=calLength2(fruit.x[i],fruit.y[i],mom.x,mom.y);
        if(l<900){
          fruit.dead(i);
          data.fruitNum++;
          mom.bigCount++;
          if(mom.bigCount>7){
            mom.bigCount=7;
          }
          if(fruit.fruitType[i]=="blue"){
            data.double=2;
          }
        }
      }
    }
  }
}
//小鱼吃果实
function momBabyCollision(){
  if(!data.gameOver){
    var l=calLength2(mom.x,mom.y,baby.x,baby.y);
    if(data.fruitNum>0){
      if(l<900){
        baby.babyFadeCount=0;

        mom.bigCount=0;
        data.addScore();
      }
    }
  }
}
