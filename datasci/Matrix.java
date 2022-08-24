package datasci;

import java.util.*;
import java.lang.Math;

public class Matrix {

  private double[][] data;
  public int nrows;
  public int ncols;

  public Matrix(int m, int n){
    data = new double[m][n];
    nrows = m;
    ncols = n;
  }

  public Matrix(double[][] d){
    data = d;
    nrows = d.length;
    ncols = d[0].length;
  }

/*
Methods for printing and copying arrays
*/
  public void print(){
    // Prints matrix in tabular form
    for (double[] d: data){
      System.out.println(Arrays.toString(d));
    }
    System.out.println();
  }


  public Matrix copy(){
    double[][] copiedData = this.data.clone();
    Matrix copy = new Matrix(copiedData);
    return copy;
  }


  private static boolean dimensionCheck(Matrix a, Matrix b){
    // Returns true if matrices have the same dimensions
    return ((a.nrows == b.nrows) && (a.ncols == b.ncols));
  }


  public double[][] getData(){
    // accessor for matrix data
    return data;
  }

/*
Matrix operations below
*/

  public void add(Matrix b){
    // Adds matrix b to this matrix
    if (dimensionCheck(this, b)) {
      for (int i = 0; i < nrows; i++){
        for (int j = 0; j < ncols; j++){
          data[i][j] += b.getData()[i][j];
        }
      }
    } else {
      System.out.println("Dimensions Invalid");
    }
  }


  public void subtract(Matrix b){
    // subtracts matrix b from this matrix
    if (dimensionCheck(this, b)) {
      for (int i = 0; i < nrows; i++){
        for (int j = 0; j < ncols; j++){
          data[i][j] -= b.getData()[i][j];
        }
      }
    } else {
      System.out.println("Dimensions Invalid");
    }
  }


  public static Matrix multiply(Matrix a, Matrix b){
    // multiplies matrices a and b
    Matrix toReturn = new Matrix(a.nrows, b.ncols);
    Matrix btransposed = transpose(b);
    if (a.ncols == b.nrows){
      for (int i = 0; i < toReturn.nrows; i++){
        for (int j = 0; j < toReturn.ncols; j++){
          toReturn.data[i][j] = Matrix.dot(a.data[i], btransposed.data[j]);
        }
      }
    } else {
      System.out.println("Dimensions Invalid for Multiplication");
      return null;
    }
    return toReturn;
  }


  private static double dot(double[] x, double[] y){
    // returns dot product of 1d arrays
    double sum = 0;
    for (int i = 0; i < x.length; i++){
      sum += x[i] * y[i];
    }
    return sum;
  }


  public static Matrix transpose(Matrix a) {
    // returns the transpose of array a
    Matrix newMatrix = new Matrix(a.ncols, a.nrows);
    for (int i = 0; i < a.nrows; i++){
      for (int j = 0; j < a.ncols; j++){
        newMatrix.data[j][i] = a.getData()[i][j];
      }
    }
    return newMatrix;
  }


  public static double determinant(Matrix a){
    // returns the determinant of square matrix a
    if (a.nrows != a.ncols){
      System.out.println("Matrix must be square");
      return (Double) null;
    }
    double[][] mat = a.getData();
    double det;
    if (a.nrows == 1){
      return mat[0][0];
    }
    else {
      det = 0;
      for (int i = 0; i < a.ncols; i++) {
        double[][] valsToKeep = new double[a.nrows-1][a.nrows-1];
        for (int j = 1; j < a.nrows; j++){
          int cur = 0;
          for (int k = 0; k < a.ncols; k++){
            if (k==i){continue;}
            valsToKeep[j-1][cur] = mat[j][k];
            cur++;
            if (cur==a.nrows-1){break;}
          }
        }
        Matrix smallerMat = new Matrix(valsToKeep);
        det += Math.pow(-1, i) * mat[0][i] * determinant(smallerMat);
      }
    }
    return det;
  }


  private static Matrix getMinor(Matrix a, int i, int j){
    // gets the "minot" matrix given a matrix and coordinates. Used in
    // cofactor method
    double[][] minor = new double[a.nrows-1][a.ncols-1];
    double[][] arr = a.getData();
    int rowCount = 0;
    int colCount = 0;
    for (int m = 0; m < a.nrows; m++){
      if (m==i){continue;}
      for (int n = 0; n < a.ncols; n++){
        if (n==j){continue;}
        minor[rowCount][colCount] = arr[m][n];
        colCount++;
        if (colCount == a.ncols-1){
          colCount=0;
          rowCount++;}
      }
      if (rowCount == a.nrows-1){break;}
    }
    Matrix minorMat = new Matrix(minor);
    return minorMat;
  }


  public static Matrix cofactor(Matrix a){
    // returns the cofactor matrix for square matrix a
    double[][] cof = new double[a.nrows][a.ncols];
    for (int i = 0; i < a.nrows; i++){
      for (int j = 0; j < a.ncols; j++){
        Matrix minor = getMinor(a, i, j);
        double determinant = determinant(getMinor(a, i, j));
        double toAdd = Math.pow(-1, (i+j)) * determinant;
        cof[i][j] = toAdd;
      }
    }
    Matrix cofMat = new Matrix(cof);
    return cofMat;
  }


  public static Matrix inverse(Matrix a){
    // returns the inverse matrix of square matrix a with non-zero determinant
    Matrix adj =  transpose(cofactor(a));
    double[][] adjData = adj.data;
    double[][] invData =  new double[a.nrows][a.ncols];
    double det = determinant(a);
    for (int i = 0; i < a.nrows; i++){
      for (int j = 0; j < a.ncols; j++){
        invData[i][j] = adjData[i][j] / det;
      }
    }
    Matrix inv = new Matrix(invData);
    return inv;
  }


  public static Matrix eye(int n){
    // returns identity matrix of dimension n
    double[][] idArr = new double[n][n];
    for (int i = 0; i < n; i++){
      for (int j = 0; j < n; j++){
        if (i == j){
          idArr[i][j] = 1;
        } else{
          idArr[i][j] = 0;
        }
      }
    }
    Matrix idMat = new Matrix(idArr);
    return idMat;
  }


  public static void main(String[] args){
    double[][] arr = {{1,3,5}, {3,4,8}, {4,7,7}};
    double[][] arr2 = {{4,6,8}, {3,1,6}, {1,2,3}};
    Matrix newMat = new Matrix(arr);
    Matrix newMat2 = newMat.copy();
    newMat2.print();
    Matrix id3 = Matrix.eye(3);
    id3.print();
  }

}
