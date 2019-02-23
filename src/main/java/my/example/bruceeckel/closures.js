function makeFun(){
  let n = 0;
  let runTimeFunction = function (arg){
    const result = "n="+ n+ " arg="+arg+":";
    arg +=1;
    n += arg;
    return result + n;
  }
  return runTimeFunction;
}

const x = makeFun();
const y = makeFun();

for(let i = 0 ; i < 5 ; i ++ ){
  console.log(x(i));
}


for(let i = 10 ; i < 15 ; i ++ ){
  console.log(y(i));
}
