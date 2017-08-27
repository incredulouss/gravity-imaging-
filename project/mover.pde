class mover
{
PVector loc;
PVector vel;
PVector acc;
float mass;

mover(float z,float q,float m)
{
  loc = new PVector(z,q);
  acc = new PVector(0,0);
  vel = new PVector(0,0);
  mass= m;  
 }
 
 void display()
 {
//   k.loadPixels();
   noStroke();
   stroke(0);
  // strokeWeight(2);
   fill(0);
   ellipse(loc.x,loc.y,mass*2,mass*2);
 }
 void applyForce(PVector f)
 {
   PVector force = PVector.div(f,mass);
   acc.add(force);
 }
 void update()
 {
   loc.add(vel);
   vel.add(acc);
   acc.mult(0);
 }
   
}