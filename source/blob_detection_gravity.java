import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.video.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class blob_detection_gravity extends PApplet {




// variables 
boolean check= true ;
Capture video;
attractor a;
float threshold =20;
mover m[]= new mover[5];
//PImage k;
float c=0.5f;
  float distThreshold=75;
int trackcolor = color(255,0,0);
ArrayList<blob> blobs = new ArrayList<blob>();
int col =200;


// setup function to run the functions
public void setup()
{

background(255);
//bolbs = new ArrayList<blob>();
String[] cameras = Capture.list();
printArray(cameras);
video = new Capture(this,cameras[4]);
video.start();
 //k= loadImage("jdnm.jpg");
 a = new attractor(width/2,height/2,40,c);
 for(int i=0;i<m.length;i++)
  m[i] = new mover(random(0,width),random(0,height),random(0,5));

}

public void captureEvent(Capture video)
{
  video.read();
}
public void draw()
{
 video.loadPixels();
 loadPixels();
 if(check)
 {
 
   image(video,0,0);
 }
 blobs.clear();
 
 for(int x=0;x<video.width;x++)
 {
   for(int y=0;y<video.height;y++)
   {
     int loc = x+y*video.width;
     int pixelColor = video.pixels[loc];
     float r1 =red(pixelColor); 
     float g1 = green(pixelColor);
     float b1 = blue(pixelColor);
     float r2 = red(trackcolor);
     float g2 = green(trackcolor);
     float b2 =blue(trackcolor);
     
     float dist= distSq(r1,g1,b1,r2,g2,b2);
     
   if(dist<threshold*threshold)
   {
      boolean found = false;
      for(blob b :blobs)
      {
        if(b.isNear(x,y))
        {
          b.add(x,y);
          found= true;
          break;}}
      
      if(!found)
      {
        blob b = new blob(x,y,col);
        blobs.add(b);
      }}
      
 }}
for(blob b : blobs)
{
 if(b.size()>500)
b.show(); 
 
}
 
 for(blob b:blobs){
 a = new attractor(b.minx ,b.miny,40,c);}
  //a.display();
  for(int i=0;i<m.length;i++)
  {
    PVector forc= a.attract(a,m[i]);
   m[i].applyForce(forc);
  m[i].display();
  m[i].update();
  }
}

public float distSq(float x1,float y1,float z1,float x2,float y2,float z2)
{
  float d = (x2-x1)*(x2-x1)+(y2-y1)*(y2-y1)+(z2-z1)*(z2-z1);
  return d;
}
public void keyPressed()
{
  if(key == 'c')
 { check= false;
 background(255);
 col = 0;}
  if(key=='q')
  distThreshold++;
  else if(key=='w')
  distThreshold--;
   if(key == 'a')
  threshold++;
  else if(key=='s')
  threshold--;
  print(threshold);
  print(',');
  println(distThreshold);
}
public void mousePressed()
{
//  video.loadPixels();
int loc = mouseX+mouseY *video.width;  
  trackcolor = video.pixels[loc];
}

  
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
  public PVector attract(attractor a,mover m)
  {
    PVector dis=PVector.sub(loc,m.loc);
    float q=dis.mag();
    float r = constrain(q,5,20);
    float u=((g*mass*m.mass)/(r*r));
    dis.normalize();
    dis.mult(u);
    return dis;
  }
  public void display()
  {
    fill(100);
    stroke(0);
    strokeWeight(2);
    ellipse(loc.x,loc.y,mass*4,mass*4);
    
 } 
    
} 
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
 public void show()
 {
  stroke(277,0,0,c);
  fill(255,2);
  strokeWeight(3);
  rectMode(CORNERS);
  rect(minx,miny,maxx,maxy);
   
   
 }
  public void add(int x,int y)
  {
   minx= min(minx,x);
   maxx= max(maxx,x);
     miny= min(miny,y);
   maxy= max(maxy,y);
  }
  public boolean isNear(int x,int y)
  {
    int cx = (maxx+minx)/2;
    int cy = (maxy+miny)/2;
    float dist = distSq(cx,cy,x,y);
    if(dist <distThreshold*distThreshold)
    return true;
    else return false;
  }    
  
  public float distSq(float x1,float y1,float x2,float y2)
{
  float d = (x2-x1)*(x2-x1)+(y2-y1)*(y2-y1);
  return d;
}
public float size()
{
 float s = (maxx-minx)*(maxy-miny);
 return s;
  
}
  
}
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
 
 public void display()
 {
//   k.loadPixels();
   noStroke();
   stroke(0);
  // strokeWeight(2);
   fill(0);
   ellipse(loc.x,loc.y,mass*2,mass*2);
 }
 public void applyForce(PVector f)
 {
   PVector force = PVector.div(f,mass);
   acc.add(force);
 }
 public void update()
 {
   loc.add(vel);
   vel.add(acc);
   acc.mult(0);
 }
   
}
  public void settings() { 
size(600,600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "blob_detection_gravity" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
