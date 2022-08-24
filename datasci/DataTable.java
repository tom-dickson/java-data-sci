package datasci;
import java.util.ArrayList;
import java.util.List;

public class DataTable {
  private ArrayList<String> columns = new ArrayList<String>();
  private ArrayList<List> data;
  private int nRows = 0;
  private int nCols;

  public DataTable(String[] cols){ // Initialize with array of column names
    for (String i: cols){
      columns.add(i);
    }
    nCols = cols.length;
    data = new ArrayList<List>(nCols);
    for (int i  = 0; i < nCols; i++){
      data.add(new ArrayList<Object>());
    }
  }


  public void addRow(List<Object> row){ // Add a row by passing a list of items
    if (row.size() != nCols){
      System.out.println("Row must have length # of columns");
    } else {
      for (int i = 0; i < nCols; i++){
        List current = data.get(i);
        current.add(row.get(i));
      }
    }
    nRows++;
  }


  public List getColumn(int i) throws IndexOutOfBoundsException{
    if (i >= nCols){
      throw new IndexOutOfBoundsException("Index not in column range");
    }
    return data.get(i);
  }


  public List getRow(int i) throws IndexOutOfBoundsException{
    if (i >= nRows){
      throw new IndexOutOfBoundsException("Index not in row range");
    }
    List<Object> toReturn = new ArrayList<Object>();
    for (List l: data){
      toReturn.add(l.get(i));
    }
    return toReturn;
  }

  public void print(){
    for (String col: columns){
      System.out.print(col + " | ");
    }
    System.out.println();
    for (int i = 0; i < nRows; i++){
      for (List l: data){
        System.out.print(l.get(i) + " | ");
      }
      System.out.println();
    }
  }

  public int nRows(){
    return nRows;
  }

  public int nCols(){
    return nCols;
  }

  public String size(){
    return (Integer.toString(nRows) + " Rows, " + Integer.toString(nCols) + " Cols");
  }

}
