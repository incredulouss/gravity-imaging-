class attractor
{
  PVector loc;
  //PVector acc;
  //PVector vel;
  float mass;
  float g;
  attractor(float x , float y,float m,float g_)
  {
    loc = new PVector(x,y);
    //vel=  new PVector(0,0);
    //acc = new PVector(0,0);
    mass= m;
    g= g_;
  }
  PVector attract(attractor a,mover m)
  {
    PVector dis=PVector.sub(loc,m.loc);
    float q=dis.mag();
    float r = constrain(q,5,20);
    float u=((g*mass*m.mass)/(r*r));
    dis.normalize();
    dis.mult(u);
    return dis;
  }
  void display()
  {
    fill(100);
    stroke(0);
    strokeWeight(2);
    ellipse(loc.x,loc.y,mass*4,mass*4);
    
 } 
    
} 