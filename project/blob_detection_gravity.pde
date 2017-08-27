import processing.video.*;


// variables 
boolean check= true ;
Capture video;
attractor a;
float threshold =20;
mover m[]= new mover[5];
//PImage k;
float c=0.5;
  float distThreshold=75;
color trackcolor = color(255,0,0);
ArrayList<blob> blobs = new ArrayList<blob>();
int col =200;


// setup function to run the functions
void setup()
{
size(600,600);
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

void captureEvent(Capture video)
{
  video.read();
}
void draw()
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
     color pixelColor = video.pixels[loc];
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

float distSq(float x1,float y1,float z1,float x2,float y2,float z2)
{
  float d = (x2-x1)*(x2-x1)+(y2-y1)*(y2-y1)+(z2-z1)*(z2-z1);
  return d;
}
void keyPressed()
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
void mousePressed()
{
//  video.loadPixels();
int loc = mouseX+mouseY *video.width;  
  trackcolor = video.pixels[loc];
}

  