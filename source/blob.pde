class blob
{
  int minx,miny,maxx,maxy;
int c;
  blob(int x,int y,int cc)
  {
    c = cc;
    minx=x;
    maxx=x;
    miny=y;
    maxy=y;
  }
 void show()
 {
  stroke(277,0,0,c);
  fill(255,2);
  strokeWeight(3);
  rectMode(CORNERS);
  rect(minx,miny,maxx,maxy);
   
   
 }
  void add(int x,int y)
  {
   minx= min(minx,x);
   maxx= max(maxx,x);
     miny= min(miny,y);
   maxy= max(maxy,y);
  }
  boolean isNear(int x,int y)
  {
    int cx = (maxx+minx)/2;
    int cy = (maxy+miny)/2;
    float dist = distSq(cx,cy,x,y);
    if(dist <distThreshold*distThreshold)
    return true;
    else return false;
  }    
  
  float distSq(float x1,float y1,float x2,float y2)
{
  float d = (x2-x1)*(x2-x1)+(y2-y1)*(y2-y1);
  return d;
}
float size()
{
 float s = (maxx-minx)*(maxy-miny);
 return s;
  
}
  
}