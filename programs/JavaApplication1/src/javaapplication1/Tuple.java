/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

/**
 *
 * @author sambit
 */
public class Tuple<X, Y, Z> { 
  public final X x; 
  public final Y y; 
  public final Z z;
  public Tuple(X x, Y y, Z z) { 
    this.x = x; 
    this.y = y; 
    this.z = z;
  } 

    public void prettyPrint() {
        System.out.print("First : "+this.x + " Second : "+this.y + " third : "+this.z);
    }

    @Override
    public String toString() {
        return "First : "+this.x + " Second : "+this.y + " third : "+this.z;
    }
    
    
} 
