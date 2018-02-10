package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.Input.InputColor;

public class SceneController 
{
	@FXML
	TextField file1;
	@FXML
	TextField file2;
	@FXML
	TableView<Input> menu1;
	@FXML
	TableView<Input> menu2;
	@FXML
	TableColumn<Input,String> file1line;
	@FXML
	TableColumn<Input,String> file2line;

	ArrayList<Input> file1str=null;
	ArrayList<Input> file2str=null;

	@FXML
	private void initialize() {
		file1str=new ArrayList<Input>();
		file2str=new ArrayList<Input>();
	}
	public void setText1(String str)
	{
		file1.setText(str);
	}
	public void setText2(String str)
	{
		file2.setText(str);
	}
	@FXML
	public void selectBtn1()
	{
		setText1(Main.select());
	}
	@FXML
	public void selectBtn2()
	{
		setText2(Main.select());
	}
	@FXML
	public void openBtn2()
	{
		file2str.clear();
		try
		{
			List<String> temp=getFile(file2.getText());
			for(String s:temp)
			{
				file2str.add(new Input(s));
			}
		}
		catch(IOException e){
			return;
		}
		ObservableList<Input> list=FXCollections.observableArrayList(file2str);
		menu2.setItems(list);
		file2line.setCellFactory(tablecol -> {
			TableCell<Input, String> cell = new TableCell<Input, String>()
			{
				
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					int index=getTableRow().getIndex();
					int bound=file2str.size();
					if(index>=0&&index<bound)
					{
	                    setStyle("-fx-background-color: "+file2str.get(index).getcolor());
					}
				}
			};
			Text text = new Text();
			cell.setGraphic(text);
			text.wrappingWidthProperty().bind(cell.widthProperty());
			text.textProperty().bind(cell.itemProperty());
			return cell;
		});
		file2line.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getLine()));

	}
	@FXML
	public void openBtn1()
	{
		file1str.clear();
		try
		{
			List<String> temp=getFile(file1.getText());
			for(String s:temp)
			{
				file1str.add(new Input(s));
			}

		}
		catch(IOException e){
			return;
		}
		ObservableList<Input> list=FXCollections.observableArrayList(file1str);
		menu1.setItems(list);
		file1line.setCellFactory(tablecol -> {
			TableCell<Input, String> cell = new TableCell<Input, String>()
			{
				
				@Override
				public void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					//System.out.println(getTableRow().getIndex());
					int index=getTableRow().getIndex();
					int bound=file1str.size();
					if(index>=0&&index<bound)
					{
	                    setStyle("-fx-background-color: "+file1str.get(index).getcolor());
					}
				}
			};
			Text text = new Text();
			cell.setGraphic(text);
			text.wrappingWidthProperty().bind(cell.widthProperty());
			text.textProperty().bind(cell.itemProperty());
			
			return cell;
		});
		file1line.setCellValueFactory(cellData->new SimpleStringProperty(cellData.getValue().getLine()));
	}	
	public List<String> getFile(String filename) throws IOException
	{
		Path p=Paths.get(filename);
		return Files.readAllLines(p);
	}
	@FXML
	public void update()
	{
		int file1index=0;
		int file2index=0;
		int token=1;
		while(file1index!=file1str.size()&& file2index!=file2str.size())
		{
			if(token==1)
			{
				int result=findTwo(file1str.get(file1index).getLine(),file2index);
				if(result!=-1)
				{
					file1str.get(file1index).setChecek(InputColor.MATCH);
					for(int i=file2index;i<result;i++)
					{
						file2str.get(i).setChecek(InputColor.UNMATCH);
					}
					file2str.get(result).setChecek(InputColor.MATCH);
					file2index=result+1;
				}
				else
				{
					file1str.get(file1index).setChecek(InputColor.UNMATCH);
					token=2;
				}
				file1index++;
			}
			else
			{
				int result=findOne(file2str.get(file2index).getLine(),file1index);
				if(result!=-1)
				{
					file2str.get(file2index).setChecek(InputColor.MATCH);
					for(int i=file1index;i<result;i++)
					{
						file1str.get(i).setChecek(InputColor.UNMATCH);
					}
					file1str.get(result).setChecek(InputColor.MATCH);
					file1index=result+1;
				}
				else
				{
					file2str.get(file2index).setChecek(InputColor.UNMATCH);
					token=1;
				}
				file2index++;
			}
		}
		for(int i=file1index;i<file1str.size();i++)
		{
			file1str.get(i).setChecek(InputColor.UNMATCH);
		}
		for(int i=file2index;i<file2str.size();i++)
		{
			file2str.get(i).setChecek(InputColor.UNMATCH);
		}
		//update
		file1line.setVisible(false);
		file1line.setVisible(true);
		file2line.setVisible(false);
		file2line.setVisible(true);
	}
	private int findTwo(String str,int startFrom)
	{
		for(int i=startFrom;i<file2str.size();i++)
		{
			if(file2str.get(i).getLine().equals(str))
				return i;
		}
		return -1;
	}
	private int findOne(String str,int startFrom)
	{
		for(int i=startFrom;i<file1str.size();i++)
		{
			if(file1str.get(i).getLine().equals(str))
				return i;
		}
		return -1;
	}
}
