// Description: Java 25 JavaFX List of Obj Pane implementation for Value.

/*
 *	Mark's Code Fractal 3.1 CFBam - Business Application Model
 *
 *	Copyright (c) 2016-2026 Mark Stephen Sobkow
 *
 *	These files are part of Mark's Code Fractal CFBam.
 *
 *	Mark's Code Fractal CFBam is available under dual commercial license from
 *	Mark Stephen Sobkow, or under the terms of the GNU General Public License,
 *	Version 3 or later with classpath and static linking exceptions.
 *
 *	As a special exception, Mark Sobkow gives you permission to link this library
 *	with independent modules to produce an executable, provided that none of them
 *	conflict with the intent of the GPLv3; that is, you are not allowed to invoke
 *	the methods of this library from non-GPLv3-compatibly licensed code. You may not
 *	implement an LPGLv3 "wedge" to try to bypass this restriction. That said, code which
 *	does not rely on this library is free to specify whatever license its authors decide
 *	to use. Mark Sobkow specifically rejects the infectious nature of the GPLv3, and
 *	considers the mere act of including GPLv3 modules in an executable to be perfectly
 *	reasonable given tools like modern Java's single-jar deployment options.
 *
 *	Mark's Code Fractal CFBam is free software: you can redistribute it and/or
 *	modify it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Mark's Code Fractal CFBam is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Mark's Code Fractal CFBam.  If not, see <https://www.gnu.org/licenses/>.
 *
 *	If you wish to modify and use this code without publishing your changes,
 *	or integrate it with proprietary code, please contact Mark Stephen Sobkow
 *	for a commercial license at mark.sobkow@gmail.com
 */

package server.markhome.mcf.v3_1.cfbam.cfbamcusteditor;

import java.io.Serializable;
import java.math.*;
import java.text.*;
import java.time.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cflib.javafx.*;
import server.markhome.mcf.v3_1.cflib.javafx.CFReferenceEditor.ICFReferenceCallback;
import server.markhome.mcf.v3_1.cflib.xml.CFLibXmlUtil;
import server.markhome.mcf.v3_1.cfcore.msscf.*;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfint.cfint.*;
import server.markhome.mcf.v3_1.cfbam.cfbam.*;
import server.markhome.mcf.v3_1.cfsec.cfsecobj.*;
import server.markhome.mcf.v3_1.cfint.cfintobj.*;
import server.markhome.mcf.v3_1.cfbam.cfbamobj.*;
import server.markhome.mcf.v3_1.cfsec.cfsecsaxloader.*;
import server.markhome.mcf.v3_1.cfint.cfintsaxloader.*;
import server.markhome.mcf.v3_1.cfbam.cfbamsaxloader.*;
import server.markhome.mcf.v3_1.cfsec.cfsecjavafx.*;
import server.markhome.mcf.v3_1.cfint.cfintjavafx.*;
import server.markhome.mcf.v3_1.cfbam.cfbamjavafx.*;
import server.markhome.mcf.v3_1.cfbam.cfbamram.*;
import server.markhome.mcf.v3_1.cfbam.cfbammsscf.*;
import server.markhome.mcf.v3_1.cfbam.cfbamcustmssbamcf.*;
import server.markhome.mcf.v3_1.cfbam.cfbamcustxmlloader.*;
import server.markhome.mcf.v3_1.cfsec.cfseccustfx.*;
import server.markhome.mcf.v3_1.cfint.cfintcustfx.*;
import server.markhome.mcf.v3_1.cfbam.cfbamcustfx.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 *	CFBamJavaFXValueListPane JavaFX List of Obj Pane implementation
 *	for Value.
 */
public class CFBamCustEditorValueListPane
extends CFBorderPane
implements ICFBamJavaFXValuePaneList
{
	public static String S_FormName = "List Value";
	protected ICFBamJavaFXSchema javafxSchema = null;
	protected Collection<ICFBamValueObj> javafxDataCollection = null;
	protected ObservableList<ICFBamValueObj> observableListOfValue = null;
	protected ScrollPane scrollMenu = null;
	protected CFHBox hboxMenu = null;
	protected ScrollPane scrollMenuAdd = null;
	protected CFVBox vboxMenuAdd = null;
	protected CFButton buttonAdd = null;
	protected CFButton buttonCancelAdd = null;
	protected CFButton buttonAddTableCol = null;
	protected CFButton buttonAddBlobCol = null;
	protected CFButton buttonAddBlobType = null;
	protected CFButton buttonAddBoolCol = null;
	protected CFButton buttonAddBoolType = null;
	protected CFButton buttonAddInt16Col = null;
	protected CFButton buttonAddInt16Type = null;
	protected CFButton buttonAddId16Gen = null;
	protected CFButton buttonAddEnumType = null;
	protected CFButton buttonAddInt32Col = null;
	protected CFButton buttonAddInt32Type = null;
	protected CFButton buttonAddId32Gen = null;
	protected CFButton buttonAddInt64Col = null;
	protected CFButton buttonAddInt64Type = null;
	protected CFButton buttonAddId64Gen = null;
	protected CFButton buttonAddUInt16Col = null;
	protected CFButton buttonAddUInt16Type = null;
	protected CFButton buttonAddUInt32Col = null;
	protected CFButton buttonAddUInt32Type = null;
	protected CFButton buttonAddUInt64Col = null;
	protected CFButton buttonAddUInt64Type = null;
	protected CFButton buttonAddFloatCol = null;
	protected CFButton buttonAddFloatType = null;
	protected CFButton buttonAddDoubleCol = null;
	protected CFButton buttonAddDoubleType = null;
	protected CFButton buttonAddNumberCol = null;
	protected CFButton buttonAddNumberType = null;
	protected CFButton buttonAddStringCol = null;
	protected CFButton buttonAddStringType = null;
	protected CFButton buttonAddTextCol = null;
	protected CFButton buttonAddTextType = null;
	protected CFButton buttonAddNmTokenCol = null;
	protected CFButton buttonAddNmTokenType = null;
	protected CFButton buttonAddNmTokensCol = null;
	protected CFButton buttonAddNmTokensType = null;
	protected CFButton buttonAddTokenCol = null;
	protected CFButton buttonAddTokenType = null;
	protected CFButton buttonAddDateCol = null;
	protected CFButton buttonAddDateType = null;
	protected CFButton buttonAddTimeCol = null;
	protected CFButton buttonAddTimeType = null;
	protected CFButton buttonAddTimestampCol = null;
	protected CFButton buttonAddTimestampType = null;
	protected CFButton buttonAddTZDateCol = null;
	protected CFButton buttonAddTZDateType = null;
	protected CFButton buttonAddTZTimeCol = null;
	protected CFButton buttonAddTZTimeType = null;
	protected CFButton buttonAddTZTimestampCol = null;
	protected CFButton buttonAddTZTimestampType = null;
	protected CFButton buttonAddUuidCol = null;
	protected CFButton buttonAddUuidType = null;
	protected CFButton buttonAddUuidGen = null;
	protected CFButton buttonMoveUpSelected = null;
	protected CFButton buttonMoveDownSelected = null;
	protected CFButton buttonViewSelected = null;
	protected CFButton buttonEditSelected = null;
	protected CFButton buttonDeleteSelected = null;
	protected TableView<ICFBamValueObj> dataTable = null;
	protected TableColumn<ICFBamValueObj,String> tableColumnObjKind = null;
	protected TableColumn<ICFBamValueObj, Long> tableColumnId = null;
	protected TableColumn<ICFBamValueObj, String> tableColumnName = null;
	protected TableColumn<ICFBamValueObj, String> tableColumnShortName = null;
	protected TableColumn<ICFBamValueObj, String> tableColumnLabel = null;
	protected TableColumn<ICFBamValueObj, String> tableColumnShortDescription = null;
	protected TableColumn<ICFBamValueObj, String> tableColumnDescription = null;
	protected TableColumn<ICFBamValueObj, Boolean> tableColumnIsNullable = null;
	protected TableColumn<ICFBamValueObj, Boolean> tableColumnGenerateId = null;
	protected TableColumn<ICFBamValueObj, ICFBamSchemaDefObj> tableColumnLookupDefSchema = null;

	public final String S_ColumnNames[] = { "Name" };
	protected ICFFormManager cfFormManager = null;
	protected boolean javafxIsInitializing = true;
	protected boolean javafxSortByChain = false;
	protected ICFBamScopeObj javafxContainer = null;
	protected ICFRefreshCallback javafxRefreshCallback = null;
	class ViewEditClosedCallback implements ICFFormClosedCallback {
		public ViewEditClosedCallback() {
		}

		@Override
		public void formClosed( ICFLibAnyObj affectedObject ) {
			if( affectedObject != null ) {
				refreshMe();
			}
		}
	}

	protected ViewEditClosedCallback viewEditClosedCallback = null;

	public ICFFormClosedCallback getViewEditClosedCallback() {
		if( viewEditClosedCallback == null ) {
			viewEditClosedCallback = new ViewEditClosedCallback();
		}
		return( viewEditClosedCallback );
	}

	class DeleteCallback implements ICFDeleteCallback {
		public DeleteCallback() {
		}

		@Override
		public void deleted( ICFLibAnyObj deletedObject ) {
			if( deletedObject != null ) {
				refreshMe();
			}
		}

		@Override
		public void formClosed( ICFLibAnyObj affectedObject ) {
			if( affectedObject != null ) {
				refreshMe();
			}
		}
	}

	protected DeleteCallback deleteCallback = null;

	public ICFDeleteCallback getDeleteCallback() {
		if( deleteCallback == null ) {
			deleteCallback = new DeleteCallback();
		}
		return( deleteCallback );
	}


	public CFBamCustEditorValueListPane( ICFFormManager formManager,
		ICFBamJavaFXSchema argSchema,
		ICFBamScopeObj argContainer,
		ICFBamValueObj argFocus,
		Collection<ICFBamValueObj> argDataCollection,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
		super();
		final String S_ProcName = "construct-schema-focus";
		if( formManager == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				"formManager" );
		}
		cfFormManager = formManager;
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				2,
				"argSchema" );
		}
		// argFocus is optional; focus may be set later during execution as
		// conditions of the runtime change.
		javafxSchema = argSchema;
		javaFXFocus = argFocus;
		javafxContainer = argContainer;
		javafxRefreshCallback = refreshCallback;
		javafxSortByChain = sortByChain;
		setJavaFXDataCollection( argDataCollection );
		dataTable = new TableView<ICFBamValueObj>();
		tableColumnObjKind = new TableColumn<ICFBamValueObj,String>( "Class Code" );
		tableColumnObjKind.setCellValueFactory( new Callback<CellDataFeatures<ICFBamValueObj,String>,ObservableValue<String> >() {
			@Override public ObservableValue<String> call( CellDataFeatures<ICFBamValueObj, String> p ) {
				ICFBamValueObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String classCode = obj.getClassCode();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( classCode );
					return( observable );
				}
			}
		});
		tableColumnObjKind.setCellFactory( new Callback<TableColumn<ICFBamValueObj,String>,TableCell<ICFBamValueObj,String>>() {
			@Override public TableCell<ICFBamValueObj,String> call(
				TableColumn<ICFBamValueObj,String> arg)
			{
				return new CFStringTableCell<ICFBamValueObj>();
			}
		});
		dataTable.getColumns().add( tableColumnObjKind );
		tableColumnId = new TableColumn<ICFBamValueObj,Long>( "Id" );
		tableColumnId.setCellValueFactory( new Callback<CellDataFeatures<ICFBamValueObj,Long>,ObservableValue<Long> >() {
			public ObservableValue<Long> call( CellDataFeatures<ICFBamValueObj, Long> p ) {
				ICFBamValueObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					long value = obj.getRequiredId();
					Long wrapped = Long.valueOf( value );
					ReadOnlyObjectWrapper<Long> observable = new ReadOnlyObjectWrapper<Long>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnId.setCellFactory( new Callback<TableColumn<ICFBamValueObj,Long>,TableCell<ICFBamValueObj,Long>>() {
			@Override public TableCell<ICFBamValueObj,Long> call(
				TableColumn<ICFBamValueObj,Long> arg)
			{
				return new CFInt64TableCell<ICFBamValueObj>();
			}
		});
		dataTable.getColumns().add( tableColumnId );
		tableColumnName = new TableColumn<ICFBamValueObj,String>( "Name" );
		tableColumnName.setCellValueFactory( new Callback<CellDataFeatures<ICFBamValueObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamValueObj, String> p ) {
				ICFBamValueObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getRequiredName();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnName.setCellFactory( new Callback<TableColumn<ICFBamValueObj,String>,TableCell<ICFBamValueObj,String>>() {
			@Override public TableCell<ICFBamValueObj,String> call(
				TableColumn<ICFBamValueObj,String> arg)
			{
				return new CFStringTableCell<ICFBamValueObj>();
			}
		});
		dataTable.getColumns().add( tableColumnName );

		tableColumnIsNullable = new TableColumn<ICFBamValueObj,Boolean>( "Is Nullable" );
		tableColumnIsNullable.setCellValueFactory( new Callback<CellDataFeatures<ICFBamValueObj,Boolean>,ObservableValue<Boolean> >() {
			public ObservableValue<Boolean> call( CellDataFeatures<ICFBamValueObj, Boolean> p ) {
				ICFBamValueObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					boolean value = obj.getRequiredIsNullable();
					Boolean wrapped = Boolean.valueOf( value );
					ReadOnlyObjectWrapper<Boolean> observable = new ReadOnlyObjectWrapper<Boolean>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnIsNullable.setCellFactory( new Callback<TableColumn<ICFBamValueObj,Boolean>,TableCell<ICFBamValueObj,Boolean>>() {
			@Override public TableCell<ICFBamValueObj,Boolean> call(
				TableColumn<ICFBamValueObj,Boolean> arg)
			{
				return new CFBoolTableCell<ICFBamValueObj>();
			}
		});
		dataTable.getColumns().add( tableColumnIsNullable );

		tableColumnLookupDefSchema = new TableColumn<ICFBamValueObj, ICFBamSchemaDefObj>( "Defining Schema" );
		tableColumnLookupDefSchema.setCellValueFactory( new Callback<CellDataFeatures<ICFBamValueObj,ICFBamSchemaDefObj>,ObservableValue<ICFBamSchemaDefObj> >() {
			public ObservableValue<ICFBamSchemaDefObj> call( CellDataFeatures<ICFBamValueObj, ICFBamSchemaDefObj> p ) {
				ICFBamValueObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamSchemaDefObj ref = obj.getOptionalLookupDefSchema();
					ReadOnlyObjectWrapper<ICFBamSchemaDefObj> observable = new ReadOnlyObjectWrapper<ICFBamSchemaDefObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnLookupDefSchema.setCellFactory( new Callback<TableColumn<ICFBamValueObj,ICFBamSchemaDefObj>,TableCell<ICFBamValueObj,ICFBamSchemaDefObj>>() {
			@Override public TableCell<ICFBamValueObj,ICFBamSchemaDefObj> call(
				TableColumn<ICFBamValueObj,ICFBamSchemaDefObj> arg)
			{
				return new CFReferenceTableCell<ICFBamValueObj,ICFBamSchemaDefObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupDefSchema );
		dataTable.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<ICFBamValueObj>() {
				@Override public void changed( ObservableValue<? extends ICFBamValueObj> observable,
					ICFBamValueObj oldValue,
					ICFBamValueObj newValue )
				{
					setJavaFXFocus( newValue );
				}
			});

		tableColumnShortName = new TableColumn<ICFBamValueObj,String>( "Short Name" );
		tableColumnShortName.setCellValueFactory( new Callback<CellDataFeatures<ICFBamValueObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamValueObj, String> p ) {
				ICFBamValueObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getOptionalShortName();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnShortName.setCellFactory( new Callback<TableColumn<ICFBamValueObj,String>,TableCell<ICFBamValueObj,String>>() {
			@Override public TableCell<ICFBamValueObj,String> call(
				TableColumn<ICFBamValueObj,String> arg)
			{
				return new CFStringTableCell<ICFBamValueObj>();
			}
		});
		dataTable.getColumns().add( tableColumnShortName );

		tableColumnDescription = new TableColumn<ICFBamValueObj,String>( "Description" );
		tableColumnDescription.setCellValueFactory( new Callback<CellDataFeatures<ICFBamValueObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamValueObj, String> p ) {
				ICFBamValueObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getOptionalDescription();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnDescription.setCellFactory( new Callback<TableColumn<ICFBamValueObj,String>,TableCell<ICFBamValueObj,String>>() {
			@Override public TableCell<ICFBamValueObj,String> call(
				TableColumn<ICFBamValueObj,String> arg)
			{
				return new CFStringTableCell<ICFBamValueObj>();
			}
		});
		dataTable.getColumns().add( tableColumnDescription );

		tableColumnShortDescription = new TableColumn<ICFBamValueObj,String>( "Short Description" );
		tableColumnShortDescription.setCellValueFactory( new Callback<CellDataFeatures<ICFBamValueObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamValueObj, String> p ) {
				ICFBamValueObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getOptionalShortDescription();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnShortDescription.setCellFactory( new Callback<TableColumn<ICFBamValueObj,String>,TableCell<ICFBamValueObj,String>>() {
			@Override public TableCell<ICFBamValueObj,String> call(
				TableColumn<ICFBamValueObj,String> arg)
			{
				return new CFStringTableCell<ICFBamValueObj>();
			}
		});
		dataTable.getColumns().add( tableColumnShortDescription );

		tableColumnLabel = new TableColumn<ICFBamValueObj,String>( "Label" );
		tableColumnLabel.setCellValueFactory( new Callback<CellDataFeatures<ICFBamValueObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamValueObj, String> p ) {
				ICFBamValueObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getOptionalLabel();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnLabel.setCellFactory( new Callback<TableColumn<ICFBamValueObj,String>,TableCell<ICFBamValueObj,String>>() {
			@Override public TableCell<ICFBamValueObj,String> call(
				TableColumn<ICFBamValueObj,String> arg)
			{
				return new CFStringTableCell<ICFBamValueObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLabel );

		tableColumnGenerateId = new TableColumn<ICFBamValueObj,Boolean>( "Generate Id" );
		tableColumnGenerateId.setCellValueFactory( new Callback<CellDataFeatures<ICFBamValueObj,Boolean>,ObservableValue<Boolean> >() {
			public ObservableValue<Boolean> call( CellDataFeatures<ICFBamValueObj, Boolean> p ) {
				ICFBamValueObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					Boolean value = obj.getOptionalGenerateId();
					ReadOnlyObjectWrapper<Boolean> observable = new ReadOnlyObjectWrapper<Boolean>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnGenerateId.setCellFactory( new Callback<TableColumn<ICFBamValueObj,Boolean>,TableCell<ICFBamValueObj,Boolean>>() {
			@Override public TableCell<ICFBamValueObj,Boolean> call(
				TableColumn<ICFBamValueObj,Boolean> arg)
			{
				return new CFBoolTableCell<ICFBamValueObj>();
			}
		});
		dataTable.getColumns().add( tableColumnGenerateId );

		scrollMenu = new ScrollPane();
		scrollMenu.setVbarPolicy( ScrollBarPolicy.NEVER );
		scrollMenu.setHbarPolicy( ScrollBarPolicy.AS_NEEDED );
		scrollMenu.setFitToHeight( true );
		scrollMenu.setContent( getPanelHBoxMenu() );

		setTop( scrollMenu );
		setCenter( dataTable );
		javafxIsInitializing = false;
		if( observableListOfValue != null ) {
			setJavaFXDataCollection( observableListOfValue );
		}
		adjustListButtons();
	}

	public ICFFormManager getCFFormManager() {
		return( cfFormManager );
	}

	public void setCFFormManager( ICFFormManager value ) {
		final String S_ProcName = "setCFFormManager";
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				"value" );
		}
		cfFormManager = value;
	}

	public ICFBamJavaFXSchema getJavaFXSchema() {
		return( javafxSchema );
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		super.setPaneMode( value );
		adjustListButtons();
	}

	public void setJavaFXFocus( ICFLibAnyObj value ) {
		final String S_ProcName = "setJavaFXFocus";
		if( ( value == null ) || ( value instanceof ICFBamValueObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFBamValueObj" );
		}
		adjustListButtons();
	}

	public ICFBamValueObj getJavaFXFocusAsValue() {
		return( (ICFBamValueObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsValue( ICFBamValueObj value ) {
		setJavaFXFocus( value );
	}

	public class ValueByQualNameComparator
	implements Comparator<ICFBamValueObj>
	{
		public ValueByQualNameComparator() {
		}

		public int compare( ICFBamValueObj lhs, ICFBamValueObj rhs ) {
			if( lhs == null ) {
				if( rhs == null ) {
					return( 0 );
				}
				else {
					return( -1 );
				}
			}
			else if( rhs == null ) {
				return( 1 );
			}
			else {
				String lhsValue = lhs.getObjQualifiedName();
				String rhsValue = rhs.getObjQualifiedName();
				if( lhsValue == null ) {
					if( rhsValue == null ) {
						return( 0 );
					}
					else {
						return( -1 );
					}
				}
				else if( rhsValue == null ) {
					return( 1 );
				}
				else {
					return( lhsValue.compareTo( rhsValue ) );
				}
			}
		}
	}

	protected ValueByQualNameComparator compareValueByQualName = new ValueByQualNameComparator();

	public Collection<ICFBamValueObj> getJavaFXDataCollection() {
		return( javafxDataCollection );
	}

	public void setJavaFXDataCollection( Collection<ICFBamValueObj> value ) {
		final String S_ProcName = "setJavaFXDataCollection";
		javafxDataCollection = value;
		observableListOfValue = FXCollections.observableArrayList();
		if( javafxDataCollection != null ) {
			if( javafxSortByChain ) {
				Iterator<ICFBamValueObj> iter = javafxDataCollection.iterator();
				ICFBamValueObj head = null;
				while( ( head == null ) && iter.hasNext() ) {
					head = iter.next();
					if( null != head.getOptionalLookupPrev() ) {
						head = null;
					}
				}
				if( ( head == null ) && ( javafxDataCollection.size() > 0 ) ) {
					throw new CFLibRuntimeException( getClass(),
						S_ProcName,
						"Could not locate head of object chain" );
				}
				ICFBamValueObj cur = head;
				while( cur != null ) {
					observableListOfValue.add( cur );
					cur = cur.getOptionalLookupNext();
				}
			}
			else {
				Iterator<ICFBamValueObj> iter = javafxDataCollection.iterator();
				while( iter.hasNext() ) {
					observableListOfValue.add( iter.next() );
				}
				observableListOfValue.sort( compareValueByQualName );
			}
		}
		if( dataTable != null ) {
			dataTable.setItems( observableListOfValue );
			// Hack from stackoverflow to fix JavaFX TableView refresh issue
			((TableColumn)dataTable.getColumns().get(0)).setVisible( false );
			((TableColumn)dataTable.getColumns().get(0)).setVisible( true );
		}
	}

	protected class CompareCFButtonByText
	implements Comparator<CFButton>
	{
		public CompareCFButtonByText() {
		}

		@Override public int compare( CFButton lhs, CFButton rhs ) {
			if( lhs == null ) {
				if( rhs == null ) {
					return( 0 );
				}
				else {
					return( -1 );
				}
			}
			else if( rhs == null ) {
				return( 1 );
			}
			else {
				int retval = lhs.getText().compareTo( rhs.getText() );
				return( retval );
			}
		}
	}

	public CFHBox getPanelHBoxMenu() {
		if( hboxMenu == null ) {
			hboxMenu = new CFHBox( 10 );
				LinkedList<CFButton> list = new LinkedList<CFButton>();

				vboxMenuAdd = new CFVBox( 10 );
					buttonAddTableCol = new CFButton();
					buttonAddTableCol.setMinWidth( 200 );
					buttonAddTableCol.setText( "Add TableCol" );
					buttonAddTableCol.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamTableColObj obj = (ICFBamTableColObj)schemaObj.getTableColTableObj().newInstance();
								ICFBamTableColEditObj edit = (ICFBamTableColEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamTableObj container = (ICFBamTableObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerTable( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								CFBorderPane frame = javafxSchema.getTableColFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXTableColPaneCommon jpanelCommon = (ICFBamJavaFXTableColPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamTableObj ) {
						list.add( buttonAddTableCol );
					}
					buttonAddBlobCol = new CFButton();
					buttonAddBlobCol.setMinWidth( 200 );
					buttonAddBlobCol.setText( "Add BlobCol" );
					buttonAddBlobCol.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamBlobColObj obj = (ICFBamBlobColObj)schemaObj.getBlobColTableObj().newInstance();
								ICFBamBlobColEditObj edit = (ICFBamBlobColEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamTableObj container = (ICFBamTableObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerTable( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setRequiredIsNullable( true );
								CFBorderPane frame = javafxSchema.getBlobColFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXBlobColPaneCommon jpanelCommon = (ICFBamJavaFXBlobColPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamTableObj ) {
						list.add( buttonAddBlobCol );
					}
					buttonAddBlobType = new CFButton();
					buttonAddBlobType.setMinWidth( 200 );
					buttonAddBlobType.setText( "Add BlobType" );
					buttonAddBlobType.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamBlobTypeObj obj = (ICFBamBlobTypeObj)schemaObj.getBlobTypeTableObj().newInstance();
								ICFBamBlobTypeEditObj edit = (ICFBamBlobTypeEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setRequiredIsNullable( true );
								CFBorderPane frame = javafxSchema.getBlobTypeFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXBlobTypePaneCommon jpanelCommon = (ICFBamJavaFXBlobTypePaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddBlobType );
					}
					buttonAddBoolCol = new CFButton();
					buttonAddBoolCol.setMinWidth( 200 );
					buttonAddBoolCol.setText( "Add BoolCol" );
					buttonAddBoolCol.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamBoolColObj obj = (ICFBamBoolColObj)schemaObj.getBoolColTableObj().newInstance();
								ICFBamBoolColEditObj edit = (ICFBamBoolColEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamTableObj container = (ICFBamTableObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerTable( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								CFBorderPane frame = javafxSchema.getBoolColFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXBoolColPaneCommon jpanelCommon = (ICFBamJavaFXBoolColPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamTableObj ) {
						list.add( buttonAddBoolCol );
					}
					buttonAddBoolType = new CFButton();
					buttonAddBoolType.setMinWidth( 200 );
					buttonAddBoolType.setText( "Add BoolType" );
					buttonAddBoolType.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamBoolTypeObj obj = (ICFBamBoolTypeObj)schemaObj.getBoolTypeTableObj().newInstance();
								ICFBamBoolTypeEditObj edit = (ICFBamBoolTypeEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setRequiredIsNullable( false );
								CFBorderPane frame = javafxSchema.getBoolTypeFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXBoolTypePaneCommon jpanelCommon = (ICFBamJavaFXBoolTypePaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddBoolType );
					}
					buttonAddInt16Col = new CFButton();
					buttonAddInt16Col.setMinWidth( 200 );
					buttonAddInt16Col.setText( "Add Int16Col" );
					buttonAddInt16Col.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamInt16ColObj obj = (ICFBamInt16ColObj)schemaObj.getInt16ColTableObj().newInstance();
								ICFBamInt16ColEditObj edit = (ICFBamInt16ColEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamTableObj container = (ICFBamTableObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerTable( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								CFBorderPane frame = javafxSchema.getInt16ColFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXInt16ColPaneCommon jpanelCommon = (ICFBamJavaFXInt16ColPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamTableObj ) {
						list.add( buttonAddInt16Col );
					}
					buttonAddInt16Type = new CFButton();
					buttonAddInt16Type.setMinWidth( 200 );
					buttonAddInt16Type.setText( "Add Int16Type" );
					buttonAddInt16Type.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamInt16TypeObj obj = (ICFBamInt16TypeObj)schemaObj.getInt16TypeTableObj().newInstance();
								ICFBamInt16TypeEditObj edit = (ICFBamInt16TypeEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setRequiredIsNullable( false );
								CFBorderPane frame = javafxSchema.getInt16TypeFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXInt16TypePaneCommon jpanelCommon = (ICFBamJavaFXInt16TypePaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddInt16Type );
					}
					buttonAddId16Gen = new CFButton();
					buttonAddId16Gen.setMinWidth( 200 );
					buttonAddId16Gen.setText( "Add Id16Gen" );
					buttonAddId16Gen.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamId16GenObj obj = (ICFBamId16GenObj)schemaObj.getId16GenTableObj().newInstance();
								ICFBamId16GenEditObj edit = (ICFBamId16GenEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setOptionalInitValue( Short.valueOf( (short)0 ) );
								edit.setOptionalMinValue( Short.valueOf( (short)0 ) );
								CFBorderPane frame = javafxSchema.getId16GenFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXId16GenPaneCommon jpanelCommon = (ICFBamJavaFXId16GenPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddId16Gen );
					}
					buttonAddEnumType = new CFButton();
					buttonAddEnumType.setMinWidth( 200 );
					buttonAddEnumType.setText( "Add EnumType" );
					buttonAddEnumType.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamEnumTypeObj obj = (ICFBamEnumTypeObj)schemaObj.getEnumTypeTableObj().newInstance();
								ICFBamEnumTypeEditObj edit = (ICFBamEnumTypeEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setRequiredIsNullable( false );
								CFBorderPane frame = javafxSchema.getEnumTypeFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXEnumTypePaneCommon jpanelCommon = (ICFBamJavaFXEnumTypePaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddEnumType );
					}
					buttonAddInt32Col = new CFButton();
					buttonAddInt32Col.setMinWidth( 200 );
					buttonAddInt32Col.setText( "Add Int32Col" );
					buttonAddInt32Col.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamInt32ColObj obj = (ICFBamInt32ColObj)schemaObj.getInt32ColTableObj().newInstance();
								ICFBamInt32ColEditObj edit = (ICFBamInt32ColEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamTableObj container = (ICFBamTableObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerTable( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								CFBorderPane frame = javafxSchema.getInt32ColFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXInt32ColPaneCommon jpanelCommon = (ICFBamJavaFXInt32ColPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamTableObj ) {
						list.add( buttonAddInt32Col );
					}
					buttonAddInt32Type = new CFButton();
					buttonAddInt32Type.setMinWidth( 200 );
					buttonAddInt32Type.setText( "Add Int32Type" );
					buttonAddInt32Type.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamInt32TypeObj obj = (ICFBamInt32TypeObj)schemaObj.getInt32TypeTableObj().newInstance();
								ICFBamInt32TypeEditObj edit = (ICFBamInt32TypeEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setRequiredIsNullable( false );
								CFBorderPane frame = javafxSchema.getInt32TypeFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXInt32TypePaneCommon jpanelCommon = (ICFBamJavaFXInt32TypePaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddInt32Type );
					}
					buttonAddId32Gen = new CFButton();
					buttonAddId32Gen.setMinWidth( 200 );
					buttonAddId32Gen.setText( "Add Id32Gen" );
					buttonAddId32Gen.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamId32GenObj obj = (ICFBamId32GenObj)schemaObj.getId32GenTableObj().newInstance();
								ICFBamId32GenEditObj edit = (ICFBamId32GenEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setRequiredIsNullable( false );
								edit.setOptionalInitValue( Integer.valueOf( 0 ) );
								edit.setOptionalMinValue( Integer.valueOf( 0 ) );
								CFBorderPane frame = javafxSchema.getId32GenFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXId32GenPaneCommon jpanelCommon = (ICFBamJavaFXId32GenPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddId32Gen );
					}
					buttonAddInt64Col = new CFButton();
					buttonAddInt64Col.setMinWidth( 200 );
					buttonAddInt64Col.setText( "Add Int64Col" );
					buttonAddInt64Col.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamInt64ColObj obj = (ICFBamInt64ColObj)schemaObj.getInt64ColTableObj().newInstance();
								ICFBamInt64ColEditObj edit = (ICFBamInt64ColEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamTableObj container = (ICFBamTableObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerTable( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								CFBorderPane frame = javafxSchema.getInt64ColFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXInt64ColPaneCommon jpanelCommon = (ICFBamJavaFXInt64ColPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamTableObj ) {
						list.add( buttonAddInt64Col );
					}
					buttonAddInt64Type = new CFButton();
					buttonAddInt64Type.setMinWidth( 200 );
					buttonAddInt64Type.setText( "Add Int64Type" );
					buttonAddInt64Type.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamInt64TypeObj obj = (ICFBamInt64TypeObj)schemaObj.getInt64TypeTableObj().newInstance();
								ICFBamInt64TypeEditObj edit = (ICFBamInt64TypeEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setRequiredIsNullable( false );
								CFBorderPane frame = javafxSchema.getInt64TypeFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXInt64TypePaneCommon jpanelCommon = (ICFBamJavaFXInt64TypePaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddInt64Type );
					}
					buttonAddId64Gen = new CFButton();
					buttonAddId64Gen.setMinWidth( 200 );
					buttonAddId64Gen.setText( "Add Id64Gen" );
					buttonAddId64Gen.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamId64GenObj obj = (ICFBamId64GenObj)schemaObj.getId64GenTableObj().newInstance();
								ICFBamId64GenEditObj edit = (ICFBamId64GenEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setRequiredIsNullable( false );
								edit.setOptionalInitValue( Long.valueOf( 0L ) );
								edit.setOptionalMinValue( Long.valueOf( 0L ) );
								CFBorderPane frame = javafxSchema.getId64GenFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXId64GenPaneCommon jpanelCommon = (ICFBamJavaFXId64GenPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddId64Gen );
					}
					buttonAddUInt16Col = new CFButton();
					buttonAddUInt16Col.setMinWidth( 200 );
					buttonAddUInt16Col.setText( "Add UInt16Col" );
					buttonAddUInt16Col.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamUInt16ColObj obj = (ICFBamUInt16ColObj)schemaObj.getUInt16ColTableObj().newInstance();
								ICFBamUInt16ColEditObj edit = (ICFBamUInt16ColEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamTableObj container = (ICFBamTableObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerTable( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								CFBorderPane frame = javafxSchema.getUInt16ColFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXUInt16ColPaneCommon jpanelCommon = (ICFBamJavaFXUInt16ColPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamTableObj ) {
						list.add( buttonAddUInt16Col );
					}
					buttonAddUInt16Type = new CFButton();
					buttonAddUInt16Type.setMinWidth( 200 );
					buttonAddUInt16Type.setText( "Add UInt16Type" );
					buttonAddUInt16Type.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamUInt16TypeObj obj = (ICFBamUInt16TypeObj)schemaObj.getUInt16TypeTableObj().newInstance();
								ICFBamUInt16TypeEditObj edit = (ICFBamUInt16TypeEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setRequiredIsNullable( false );
								CFBorderPane frame = javafxSchema.getUInt16TypeFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXUInt16TypePaneCommon jpanelCommon = (ICFBamJavaFXUInt16TypePaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddUInt16Type );
					}
					buttonAddUInt32Col = new CFButton();
					buttonAddUInt32Col.setMinWidth( 200 );
					buttonAddUInt32Col.setText( "Add UInt32Col" );
					buttonAddUInt32Col.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamUInt32ColObj obj = (ICFBamUInt32ColObj)schemaObj.getUInt32ColTableObj().newInstance();
								ICFBamUInt32ColEditObj edit = (ICFBamUInt32ColEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamTableObj container = (ICFBamTableObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerTable( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								CFBorderPane frame = javafxSchema.getUInt32ColFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXUInt32ColPaneCommon jpanelCommon = (ICFBamJavaFXUInt32ColPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamTableObj ) {
						list.add( buttonAddUInt32Col );
					}
					buttonAddUInt32Type = new CFButton();
					buttonAddUInt32Type.setMinWidth( 200 );
					buttonAddUInt32Type.setText( "Add UInt32Type" );
					buttonAddUInt32Type.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamUInt32TypeObj obj = (ICFBamUInt32TypeObj)schemaObj.getUInt32TypeTableObj().newInstance();
								ICFBamUInt32TypeEditObj edit = (ICFBamUInt32TypeEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setRequiredIsNullable( false );
								CFBorderPane frame = javafxSchema.getUInt32TypeFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXUInt32TypePaneCommon jpanelCommon = (ICFBamJavaFXUInt32TypePaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddUInt32Type );
					}
					buttonAddUInt64Col = new CFButton();
					buttonAddUInt64Col.setMinWidth( 200 );
					buttonAddUInt64Col.setText( "Add UInt64Col" );
					buttonAddUInt64Col.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamUInt64ColObj obj = (ICFBamUInt64ColObj)schemaObj.getUInt64ColTableObj().newInstance();
								ICFBamUInt64ColEditObj edit = (ICFBamUInt64ColEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamTableObj container = (ICFBamTableObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerTable( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								CFBorderPane frame = javafxSchema.getUInt64ColFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXUInt64ColPaneCommon jpanelCommon = (ICFBamJavaFXUInt64ColPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamTableObj ) {
						list.add( buttonAddUInt64Col );
					}
					buttonAddUInt64Type = new CFButton();
					buttonAddUInt64Type.setMinWidth( 200 );
					buttonAddUInt64Type.setText( "Add UInt64Type" );
					buttonAddUInt64Type.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamUInt64TypeObj obj = (ICFBamUInt64TypeObj)schemaObj.getUInt64TypeTableObj().newInstance();
								ICFBamUInt64TypeEditObj edit = (ICFBamUInt64TypeEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setRequiredIsNullable( false );
								CFBorderPane frame = javafxSchema.getUInt64TypeFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXUInt64TypePaneCommon jpanelCommon = (ICFBamJavaFXUInt64TypePaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddUInt64Type );
					}
					buttonAddFloatCol = new CFButton();
					buttonAddFloatCol.setMinWidth( 200 );
					buttonAddFloatCol.setText( "Add FloatCol" );
					buttonAddFloatCol.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamFloatColObj obj = (ICFBamFloatColObj)schemaObj.getFloatColTableObj().newInstance();
								ICFBamFloatColEditObj edit = (ICFBamFloatColEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamTableObj container = (ICFBamTableObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerTable( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								CFBorderPane frame = javafxSchema.getFloatColFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXFloatColPaneCommon jpanelCommon = (ICFBamJavaFXFloatColPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamTableObj ) {
						list.add( buttonAddFloatCol );
					}
					buttonAddFloatType = new CFButton();
					buttonAddFloatType.setMinWidth( 200 );
					buttonAddFloatType.setText( "Add FloatType" );
					buttonAddFloatType.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamFloatTypeObj obj = (ICFBamFloatTypeObj)schemaObj.getFloatTypeTableObj().newInstance();
								ICFBamFloatTypeEditObj edit = (ICFBamFloatTypeEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setRequiredIsNullable( false );
								CFBorderPane frame = javafxSchema.getFloatTypeFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXFloatTypePaneCommon jpanelCommon = (ICFBamJavaFXFloatTypePaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddFloatType );
					}
					buttonAddDoubleCol = new CFButton();
					buttonAddDoubleCol.setMinWidth( 200 );
					buttonAddDoubleCol.setText( "Add DoubleCol" );
					buttonAddDoubleCol.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamDoubleColObj obj = (ICFBamDoubleColObj)schemaObj.getDoubleColTableObj().newInstance();
								ICFBamDoubleColEditObj edit = (ICFBamDoubleColEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamTableObj container = (ICFBamTableObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerTable( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								CFBorderPane frame = javafxSchema.getDoubleColFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXDoubleColPaneCommon jpanelCommon = (ICFBamJavaFXDoubleColPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamTableObj ) {
						list.add( buttonAddDoubleCol );
					}
					buttonAddDoubleType = new CFButton();
					buttonAddDoubleType.setMinWidth( 200 );
					buttonAddDoubleType.setText( "Add DoubleType" );
					buttonAddDoubleType.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamDoubleTypeObj obj = (ICFBamDoubleTypeObj)schemaObj.getDoubleTypeTableObj().newInstance();
								ICFBamDoubleTypeEditObj edit = (ICFBamDoubleTypeEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setRequiredIsNullable( false );
								CFBorderPane frame = javafxSchema.getDoubleTypeFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXDoubleTypePaneCommon jpanelCommon = (ICFBamJavaFXDoubleTypePaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddDoubleType );
					}
					buttonAddNumberCol = new CFButton();
					buttonAddNumberCol.setMinWidth( 200 );
					buttonAddNumberCol.setText( "Add NumberCol" );
					buttonAddNumberCol.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamNumberColObj obj = (ICFBamNumberColObj)schemaObj.getNumberColTableObj().newInstance();
								ICFBamNumberColEditObj edit = (ICFBamNumberColEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamTableObj container = (ICFBamTableObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerTable( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								CFBorderPane frame = javafxSchema.getNumberColFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXNumberColPaneCommon jpanelCommon = (ICFBamJavaFXNumberColPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamTableObj ) {
						list.add( buttonAddNumberCol );
					}
					buttonAddNumberType = new CFButton();
					buttonAddNumberType.setMinWidth( 200 );
					buttonAddNumberType.setText( "Add NumberType" );
					buttonAddNumberType.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamNumberTypeObj obj = (ICFBamNumberTypeObj)schemaObj.getNumberTypeTableObj().newInstance();
								ICFBamNumberTypeEditObj edit = (ICFBamNumberTypeEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setRequiredIsNullable( false );
								CFBorderPane frame = javafxSchema.getNumberTypeFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXNumberTypePaneCommon jpanelCommon = (ICFBamJavaFXNumberTypePaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddNumberType );
					}
					buttonAddStringCol = new CFButton();
					buttonAddStringCol.setMinWidth( 200 );
					buttonAddStringCol.setText( "Add StringCol" );
					buttonAddStringCol.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamStringColObj obj = (ICFBamStringColObj)schemaObj.getStringColTableObj().newInstance();
								ICFBamStringColEditObj edit = (ICFBamStringColEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamTableObj container = (ICFBamTableObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerTable( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								CFBorderPane frame = javafxSchema.getStringColFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXStringColPaneCommon jpanelCommon = (ICFBamJavaFXStringColPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamTableObj ) {
						list.add( buttonAddStringCol );
					}
					buttonAddStringType = new CFButton();
					buttonAddStringType.setMinWidth( 200 );
					buttonAddStringType.setText( "Add StringType" );
					buttonAddStringType.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamStringTypeObj obj = (ICFBamStringTypeObj)schemaObj.getStringTypeTableObj().newInstance();
								ICFBamStringTypeEditObj edit = (ICFBamStringTypeEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setRequiredIsNullable( false );
								CFBorderPane frame = javafxSchema.getStringTypeFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXStringTypePaneCommon jpanelCommon = (ICFBamJavaFXStringTypePaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddStringType );
					}
					buttonAddTextCol = new CFButton();
					buttonAddTextCol.setMinWidth( 200 );
					buttonAddTextCol.setText( "Add TextCol" );
					buttonAddTextCol.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamTextColObj obj = (ICFBamTextColObj)schemaObj.getTextColTableObj().newInstance();
								ICFBamTextColEditObj edit = (ICFBamTextColEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamTableObj container = (ICFBamTableObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerTable( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								CFBorderPane frame = javafxSchema.getTextColFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXTextColPaneCommon jpanelCommon = (ICFBamJavaFXTextColPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamTableObj ) {
						list.add( buttonAddTextCol );
					}
					buttonAddTextType = new CFButton();
					buttonAddTextType.setMinWidth( 200 );
					buttonAddTextType.setText( "Add TextType" );
					buttonAddTextType.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamTextTypeObj obj = (ICFBamTextTypeObj)schemaObj.getTextTypeTableObj().newInstance();
								ICFBamTextTypeEditObj edit = (ICFBamTextTypeEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setRequiredIsNullable( false );
								CFBorderPane frame = javafxSchema.getTextTypeFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXTextTypePaneCommon jpanelCommon = (ICFBamJavaFXTextTypePaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddTextType );
					}
					buttonAddNmTokenCol = new CFButton();
					buttonAddNmTokenCol.setMinWidth( 200 );
					buttonAddNmTokenCol.setText( "Add NmTokenCol" );
					buttonAddNmTokenCol.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamNmTokenColObj obj = (ICFBamNmTokenColObj)schemaObj.getNmTokenColTableObj().newInstance();
								ICFBamNmTokenColEditObj edit = (ICFBamNmTokenColEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamTableObj container = (ICFBamTableObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerTable( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								CFBorderPane frame = javafxSchema.getNmTokenColFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXNmTokenColPaneCommon jpanelCommon = (ICFBamJavaFXNmTokenColPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamTableObj ) {
						list.add( buttonAddNmTokenCol );
					}
					buttonAddNmTokenType = new CFButton();
					buttonAddNmTokenType.setMinWidth( 200 );
					buttonAddNmTokenType.setText( "Add NmTokenType" );
					buttonAddNmTokenType.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamNmTokenTypeObj obj = (ICFBamNmTokenTypeObj)schemaObj.getNmTokenTypeTableObj().newInstance();
								ICFBamNmTokenTypeEditObj edit = (ICFBamNmTokenTypeEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setRequiredIsNullable( false );
								CFBorderPane frame = javafxSchema.getNmTokenTypeFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXNmTokenTypePaneCommon jpanelCommon = (ICFBamJavaFXNmTokenTypePaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddNmTokenType );
					}
					buttonAddNmTokensCol = new CFButton();
					buttonAddNmTokensCol.setMinWidth( 200 );
					buttonAddNmTokensCol.setText( "Add NmTokensCol" );
					buttonAddNmTokensCol.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamNmTokensColObj obj = (ICFBamNmTokensColObj)schemaObj.getNmTokensColTableObj().newInstance();
								ICFBamNmTokensColEditObj edit = (ICFBamNmTokensColEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamTableObj container = (ICFBamTableObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerTable( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								CFBorderPane frame = javafxSchema.getNmTokensColFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXNmTokensColPaneCommon jpanelCommon = (ICFBamJavaFXNmTokensColPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamTableObj ) {
						list.add( buttonAddNmTokensCol );
					}
					buttonAddNmTokensType = new CFButton();
					buttonAddNmTokensType.setMinWidth( 200 );
					buttonAddNmTokensType.setText( "Add NmTokensType" );
					buttonAddNmTokensType.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamNmTokensTypeObj obj = (ICFBamNmTokensTypeObj)schemaObj.getNmTokensTypeTableObj().newInstance();
								ICFBamNmTokensTypeEditObj edit = (ICFBamNmTokensTypeEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setRequiredIsNullable( false );
								CFBorderPane frame = javafxSchema.getNmTokensTypeFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXNmTokensTypePaneCommon jpanelCommon = (ICFBamJavaFXNmTokensTypePaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddNmTokensType );
					}
					buttonAddTokenCol = new CFButton();
					buttonAddTokenCol.setMinWidth( 200 );
					buttonAddTokenCol.setText( "Add TokenCol" );
					buttonAddTokenCol.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamTokenColObj obj = (ICFBamTokenColObj)schemaObj.getTokenColTableObj().newInstance();
								ICFBamTokenColEditObj edit = (ICFBamTokenColEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamTableObj container = (ICFBamTableObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerTable( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								CFBorderPane frame = javafxSchema.getTokenColFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXTokenColPaneCommon jpanelCommon = (ICFBamJavaFXTokenColPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamTableObj ) {
						list.add( buttonAddTokenCol );
					}
					buttonAddTokenType = new CFButton();
					buttonAddTokenType.setMinWidth( 200 );
					buttonAddTokenType.setText( "Add TokenType" );
					buttonAddTokenType.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamTokenTypeObj obj = (ICFBamTokenTypeObj)schemaObj.getTokenTypeTableObj().newInstance();
								ICFBamTokenTypeEditObj edit = (ICFBamTokenTypeEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setRequiredIsNullable( false );
								CFBorderPane frame = javafxSchema.getTokenTypeFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXTokenTypePaneCommon jpanelCommon = (ICFBamJavaFXTokenTypePaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddTokenType );
					}
					buttonAddDateCol = new CFButton();
					buttonAddDateCol.setMinWidth( 200 );
					buttonAddDateCol.setText( "Add DateCol" );
					buttonAddDateCol.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamDateColObj obj = (ICFBamDateColObj)schemaObj.getDateColTableObj().newInstance();
								ICFBamDateColEditObj edit = (ICFBamDateColEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamTableObj container = (ICFBamTableObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerTable( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								CFBorderPane frame = javafxSchema.getDateColFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXDateColPaneCommon jpanelCommon = (ICFBamJavaFXDateColPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamTableObj ) {
						list.add( buttonAddDateCol );
					}
					buttonAddDateType = new CFButton();
					buttonAddDateType.setMinWidth( 200 );
					buttonAddDateType.setText( "Add DateType" );
					buttonAddDateType.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamDateTypeObj obj = (ICFBamDateTypeObj)schemaObj.getDateTypeTableObj().newInstance();
								ICFBamDateTypeEditObj edit = (ICFBamDateTypeEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setRequiredIsNullable( false );
								CFBorderPane frame = javafxSchema.getDateTypeFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXDateTypePaneCommon jpanelCommon = (ICFBamJavaFXDateTypePaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddDateType );
					}
					buttonAddTimeCol = new CFButton();
					buttonAddTimeCol.setMinWidth( 200 );
					buttonAddTimeCol.setText( "Add TimeCol" );
					buttonAddTimeCol.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamTimeColObj obj = (ICFBamTimeColObj)schemaObj.getTimeColTableObj().newInstance();
								ICFBamTimeColEditObj edit = (ICFBamTimeColEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamTableObj container = (ICFBamTableObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerTable( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								CFBorderPane frame = javafxSchema.getTimeColFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXTimeColPaneCommon jpanelCommon = (ICFBamJavaFXTimeColPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamTableObj ) {
						list.add( buttonAddTimeCol );
					}
					buttonAddTimeType = new CFButton();
					buttonAddTimeType.setMinWidth( 200 );
					buttonAddTimeType.setText( "Add TimeType" );
					buttonAddTimeType.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamTimeTypeObj obj = (ICFBamTimeTypeObj)schemaObj.getTimeTypeTableObj().newInstance();
								ICFBamTimeTypeEditObj edit = (ICFBamTimeTypeEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setRequiredIsNullable( false );
								CFBorderPane frame = javafxSchema.getTimeTypeFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXTimeTypePaneCommon jpanelCommon = (ICFBamJavaFXTimeTypePaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddTimeType );
					}
					buttonAddTimestampCol = new CFButton();
					buttonAddTimestampCol.setMinWidth( 200 );
					buttonAddTimestampCol.setText( "Add TimestampCol" );
					buttonAddTimestampCol.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamTimestampColObj obj = (ICFBamTimestampColObj)schemaObj.getTimestampColTableObj().newInstance();
								ICFBamTimestampColEditObj edit = (ICFBamTimestampColEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamTableObj container = (ICFBamTableObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerTable( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								CFBorderPane frame = javafxSchema.getTimestampColFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXTimestampColPaneCommon jpanelCommon = (ICFBamJavaFXTimestampColPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamTableObj ) {
						list.add( buttonAddTimestampCol );
					}
					buttonAddTimestampType = new CFButton();
					buttonAddTimestampType.setMinWidth( 200 );
					buttonAddTimestampType.setText( "Add TimestampType" );
					buttonAddTimestampType.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamTimestampTypeObj obj = (ICFBamTimestampTypeObj)schemaObj.getTimestampTypeTableObj().newInstance();
								ICFBamTimestampTypeEditObj edit = (ICFBamTimestampTypeEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setRequiredIsNullable( false );
								CFBorderPane frame = javafxSchema.getTimestampTypeFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXTimestampTypePaneCommon jpanelCommon = (ICFBamJavaFXTimestampTypePaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddTimestampType );
					}
					buttonAddTZDateCol = new CFButton();
					buttonAddTZDateCol.setMinWidth( 200 );
					buttonAddTZDateCol.setText( "Add TZDateCol" );
					buttonAddTZDateCol.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamTZDateColObj obj = (ICFBamTZDateColObj)schemaObj.getTZDateColTableObj().newInstance();
								ICFBamTZDateColEditObj edit = (ICFBamTZDateColEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamTableObj container = (ICFBamTableObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerTable( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								CFBorderPane frame = javafxSchema.getTZDateColFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXTZDateColPaneCommon jpanelCommon = (ICFBamJavaFXTZDateColPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamTableObj ) {
						list.add( buttonAddTZDateCol );
					}
					buttonAddTZDateType = new CFButton();
					buttonAddTZDateType.setMinWidth( 200 );
					buttonAddTZDateType.setText( "Add TZDateType" );
					buttonAddTZDateType.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamTZDateTypeObj obj = (ICFBamTZDateTypeObj)schemaObj.getTZDateTypeTableObj().newInstance();
								ICFBamTZDateTypeEditObj edit = (ICFBamTZDateTypeEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setRequiredIsNullable( false );
								CFBorderPane frame = javafxSchema.getTZDateTypeFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXTZDateTypePaneCommon jpanelCommon = (ICFBamJavaFXTZDateTypePaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddTZDateType );
					}
					buttonAddTZTimeCol = new CFButton();
					buttonAddTZTimeCol.setMinWidth( 200 );
					buttonAddTZTimeCol.setText( "Add TZTimeCol" );
					buttonAddTZTimeCol.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamTZTimeColObj obj = (ICFBamTZTimeColObj)schemaObj.getTZTimeColTableObj().newInstance();
								ICFBamTZTimeColEditObj edit = (ICFBamTZTimeColEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamTableObj container = (ICFBamTableObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerTable( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								CFBorderPane frame = javafxSchema.getTZTimeColFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXTZTimeColPaneCommon jpanelCommon = (ICFBamJavaFXTZTimeColPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamTableObj ) {
						list.add( buttonAddTZTimeCol );
					}
					buttonAddTZTimeType = new CFButton();
					buttonAddTZTimeType.setMinWidth( 200 );
					buttonAddTZTimeType.setText( "Add TZTimeType" );
					buttonAddTZTimeType.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamTZTimeTypeObj obj = (ICFBamTZTimeTypeObj)schemaObj.getTZTimeTypeTableObj().newInstance();
								ICFBamTZTimeTypeEditObj edit = (ICFBamTZTimeTypeEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setRequiredIsNullable( false );
								CFBorderPane frame = javafxSchema.getTZTimeTypeFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXTZTimeTypePaneCommon jpanelCommon = (ICFBamJavaFXTZTimeTypePaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddTZTimeType );
					}
					buttonAddTZTimestampCol = new CFButton();
					buttonAddTZTimestampCol.setMinWidth( 200 );
					buttonAddTZTimestampCol.setText( "Add TZTimestampCol" );
					buttonAddTZTimestampCol.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamTZTimestampColObj obj = (ICFBamTZTimestampColObj)schemaObj.getTZTimestampColTableObj().newInstance();
								ICFBamTZTimestampColEditObj edit = (ICFBamTZTimestampColEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamTableObj container = (ICFBamTableObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerTable( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								CFBorderPane frame = javafxSchema.getTZTimestampColFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXTZTimestampColPaneCommon jpanelCommon = (ICFBamJavaFXTZTimestampColPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamTableObj ) {
						list.add( buttonAddTZTimestampCol );
					}
					buttonAddTZTimestampType = new CFButton();
					buttonAddTZTimestampType.setMinWidth( 200 );
					buttonAddTZTimestampType.setText( "Add TZTimestampType" );
					buttonAddTZTimestampType.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamTZTimestampTypeObj obj = (ICFBamTZTimestampTypeObj)schemaObj.getTZTimestampTypeTableObj().newInstance();
								ICFBamTZTimestampTypeEditObj edit = (ICFBamTZTimestampTypeEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setRequiredIsNullable( false );
								CFBorderPane frame = javafxSchema.getTZTimestampTypeFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXTZTimestampTypePaneCommon jpanelCommon = (ICFBamJavaFXTZTimestampTypePaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddTZTimestampType );
					}
					buttonAddUuidCol = new CFButton();
					buttonAddUuidCol.setMinWidth( 200 );
					buttonAddUuidCol.setText( "Add UuidCol" );
					buttonAddUuidCol.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamUuidColObj obj = (ICFBamUuidColObj)schemaObj.getUuidColTableObj().newInstance();
								ICFBamUuidColEditObj edit = (ICFBamUuidColEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamTableObj container = (ICFBamTableObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerTable( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								CFBorderPane frame = javafxSchema.getUuidColFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXUuidColPaneCommon jpanelCommon = (ICFBamJavaFXUuidColPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamTableObj ) {
						list.add( buttonAddUuidCol );
					}
					buttonAddUuidType = new CFButton();
					buttonAddUuidType.setMinWidth( 200 );
					buttonAddUuidType.setText( "Add UuidType" );
					buttonAddUuidType.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamUuidTypeObj obj = (ICFBamUuidTypeObj)schemaObj.getUuidTypeTableObj().newInstance();
								ICFBamUuidTypeEditObj edit = (ICFBamUuidTypeEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setRequiredIsNullable( false );
								CFBorderPane frame = javafxSchema.getUuidTypeFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXUuidTypePaneCommon jpanelCommon = (ICFBamJavaFXUuidTypePaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddUuidType );
					}
					buttonAddUuidGen = new CFButton();
					buttonAddUuidGen.setMinWidth( 200 );
					buttonAddUuidGen.setText( "Add UuidGen" );
					buttonAddUuidGen.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamUuidGenObj obj = (ICFBamUuidGenObj)schemaObj.getUuidGenTableObj().newInstance();
								ICFBamUuidGenEditObj edit = (ICFBamUuidGenEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFSecTenantObj secTenant = schemaObj.getSecTenant();
								edit.setRequiredOwnerTenant( secTenant );
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								edit.setOptionalGenerateId( Boolean.valueOf( false ) );
								edit.setRequiredIsNullable( false );
								edit.setOptionalInitValue( UUID.randomUUID() );
								CFBorderPane frame = javafxSchema.getUuidGenFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamJavaFXUuidGenPaneCommon jpanelCommon = (ICFBamJavaFXUuidGenPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddUuidGen );
					}

				int len = list.size();
				CFButton arr[] = new CFButton[len];
				Iterator<CFButton> iter = list.iterator();
				int idx = 0;
				while( iter.hasNext() ) {
					arr[idx++] = iter.next();
				}
				Arrays.sort( arr, new CompareCFButtonByText() );
				for( idx = 0; idx < len; idx ++ ) {
					vboxMenuAdd.getChildren().add( arr[idx] );
				}

				buttonCancelAdd = new CFButton();
				buttonCancelAdd.setMinWidth( 200 );
				buttonCancelAdd.setText( "Cancel Add..." );
				buttonCancelAdd.setOnAction( new EventHandler<ActionEvent>() {
					@Override public void handle( ActionEvent e ) {
						final String S_ProcName = "handle";
						try {
							setLeft( null );
							adjustListButtons();
						}
						catch( Throwable t ) {
							CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
						}
					}
				});
				vboxMenuAdd.getChildren().add( buttonCancelAdd );

				scrollMenuAdd = new ScrollPane();
				scrollMenuAdd.setMinWidth( 240 );
				scrollMenuAdd.setMaxWidth( 240 );
				scrollMenuAdd.setFitToWidth( true );
				scrollMenuAdd.setHbarPolicy( ScrollBarPolicy.NEVER );
				scrollMenuAdd.setVbarPolicy( ScrollBarPolicy.AS_NEEDED );
				scrollMenuAdd.setContent( vboxMenuAdd );

			buttonAdd = new CFButton();
			buttonAdd.setMinWidth( 200 );
			buttonAdd.setText( "Add..." );
			buttonAdd.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						setLeft( scrollMenuAdd );
						adjustListButtons();
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			if( ( javafxContainer instanceof ICFBamSchemaDefObj )
				|| ( ( javafxContainer instanceof ICFBamTableObj )
					&& ( ((ICFBamTableObj)javafxContainer).getOptionalLookupDefSchema() == null ) ) )
			{
				hboxMenu.getChildren().add( buttonAdd );
			}

			buttonMoveUpSelected = new CFButton();
			buttonMoveUpSelected.setMinWidth( 200 );
			buttonMoveUpSelected.setText( "Move Up Selected" );
			buttonMoveUpSelected.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
						if( schemaObj == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"schemaObj" );
						}
						ICFBamValueObj selectedInstance = getJavaFXFocusAsValue();
						if( selectedInstance != null ) {
							if( null != selectedInstance.getOptionalLookupPrev() ) {
								selectedInstance.moveUp();
								refreshMe();
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			if( ( javafxContainer instanceof ICFBamSchemaDefObj )
					|| ( ( javafxContainer instanceof ICFBamTableObj )
						&& ( ((ICFBamTableObj)javafxContainer).getOptionalLookupDefSchema() == null ) ) )
				{
					if( javafxSortByChain ) {
						hboxMenu.getChildren().add( buttonMoveUpSelected );
					}
				}

			buttonMoveDownSelected = new CFButton();
			buttonMoveDownSelected.setMinWidth( 200 );
			buttonMoveDownSelected.setText( "Move Down Selected" );
			buttonMoveDownSelected.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
						if( schemaObj == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"schemaObj" );
						}
						ICFBamValueObj selectedInstance = getJavaFXFocusAsValue();
						if( selectedInstance != null ) {
							if( null != selectedInstance.getOptionalLookupNext() ) {
								selectedInstance.moveDown();
								refreshMe();
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			if( ( javafxContainer instanceof ICFBamSchemaDefObj )
					|| ( ( javafxContainer instanceof ICFBamTableObj )
						&& ( ((ICFBamTableObj)javafxContainer).getOptionalLookupDefSchema() == null ) ) )
				{
					if( javafxSortByChain ) {
						hboxMenu.getChildren().add( buttonMoveDownSelected );
					}
				}

			buttonViewSelected = new CFButton();
			buttonViewSelected.setMinWidth( 200 );
			buttonViewSelected.setText( "View Selected" );
			buttonViewSelected.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
						if( schemaObj == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"schemaObj" );
						}
						ICFBamValueObj selectedInstance = getJavaFXFocusAsValue();
						if( selectedInstance != null ) {
							String classCode = selectedInstance.getClassCode();
							if( "VALU".equals( classCode ) ) {
								CFBorderPane frame = javafxSchema.getValueFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFBamJavaFXValuePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TBLC".equals( classCode ) ) {
								ICFBamTableColObj obj = (ICFBamTableColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTableColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTableColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "ATOM".equals( classCode ) ) {
								ICFBamAtomObj obj = (ICFBamAtomObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getAtomFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXAtomPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "BLBD".equals( classCode ) ) {
								ICFBamBlobDefObj obj = (ICFBamBlobDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getBlobDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXBlobDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "BLBC".equals( classCode ) ) {
								ICFBamBlobColObj obj = (ICFBamBlobColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getBlobColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXBlobColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "BLBT".equals( classCode ) ) {
								ICFBamBlobTypeObj obj = (ICFBamBlobTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getBlobTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXBlobTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "BOLD".equals( classCode ) ) {
								ICFBamBoolDefObj obj = (ICFBamBoolDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getBoolDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXBoolDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "BOLC".equals( classCode ) ) {
								ICFBamBoolColObj obj = (ICFBamBoolColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getBoolColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXBoolColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "BOLT".equals( classCode ) ) {
								ICFBamBoolTypeObj obj = (ICFBamBoolTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getBoolTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXBoolTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "I16D".equals( classCode ) ) {
								ICFBamInt16DefObj obj = (ICFBamInt16DefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getInt16DefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXInt16DefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "I16C".equals( classCode ) ) {
								ICFBamInt16ColObj obj = (ICFBamInt16ColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getInt16ColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXInt16ColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "I16T".equals( classCode ) ) {
								ICFBamInt16TypeObj obj = (ICFBamInt16TypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getInt16TypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXInt16TypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "IG16".equals( classCode ) ) {
								ICFBamId16GenObj obj = (ICFBamId16GenObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getId16GenFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXId16GenPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "ENMD".equals( classCode ) ) {
								ICFBamEnumDefObj obj = (ICFBamEnumDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getEnumDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXEnumDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "ENMT".equals( classCode ) ) {
								ICFBamEnumTypeObj obj = (ICFBamEnumTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getEnumTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXEnumTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "I32D".equals( classCode ) ) {
								ICFBamInt32DefObj obj = (ICFBamInt32DefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getInt32DefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXInt32DefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "I32C".equals( classCode ) ) {
								ICFBamInt32ColObj obj = (ICFBamInt32ColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getInt32ColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXInt32ColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "I32T".equals( classCode ) ) {
								ICFBamInt32TypeObj obj = (ICFBamInt32TypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getInt32TypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXInt32TypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "IG32".equals( classCode ) ) {
								ICFBamId32GenObj obj = (ICFBamId32GenObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getId32GenFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXId32GenPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "I64D".equals( classCode ) ) {
								ICFBamInt64DefObj obj = (ICFBamInt64DefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getInt64DefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXInt64DefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "I64C".equals( classCode ) ) {
								ICFBamInt64ColObj obj = (ICFBamInt64ColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getInt64ColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXInt64ColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "I64T".equals( classCode ) ) {
								ICFBamInt64TypeObj obj = (ICFBamInt64TypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getInt64TypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXInt64TypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "IG64".equals( classCode ) ) {
								ICFBamId64GenObj obj = (ICFBamId64GenObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getId64GenFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXId64GenPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "U16D".equals( classCode ) ) {
								ICFBamUInt16DefObj obj = (ICFBamUInt16DefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUInt16DefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXUInt16DefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "U16C".equals( classCode ) ) {
								ICFBamUInt16ColObj obj = (ICFBamUInt16ColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUInt16ColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXUInt16ColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "U16T".equals( classCode ) ) {
								ICFBamUInt16TypeObj obj = (ICFBamUInt16TypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUInt16TypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXUInt16TypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "U32D".equals( classCode ) ) {
								ICFBamUInt32DefObj obj = (ICFBamUInt32DefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUInt32DefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXUInt32DefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "U32C".equals( classCode ) ) {
								ICFBamUInt32ColObj obj = (ICFBamUInt32ColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUInt32ColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXUInt32ColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "U32T".equals( classCode ) ) {
								ICFBamUInt32TypeObj obj = (ICFBamUInt32TypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUInt32TypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXUInt32TypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "U64D".equals( classCode ) ) {
								ICFBamUInt64DefObj obj = (ICFBamUInt64DefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUInt64DefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXUInt64DefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "U64C".equals( classCode ) ) {
								ICFBamUInt64ColObj obj = (ICFBamUInt64ColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUInt64ColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXUInt64ColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "U64T".equals( classCode ) ) {
								ICFBamUInt64TypeObj obj = (ICFBamUInt64TypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUInt64TypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXUInt64TypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "FLTD".equals( classCode ) ) {
								ICFBamFloatDefObj obj = (ICFBamFloatDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getFloatDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXFloatDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "FLTC".equals( classCode ) ) {
								ICFBamFloatColObj obj = (ICFBamFloatColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getFloatColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXFloatColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "FLTT".equals( classCode ) ) {
								ICFBamFloatTypeObj obj = (ICFBamFloatTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getFloatTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXFloatTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "DBLD".equals( classCode ) ) {
								ICFBamDoubleDefObj obj = (ICFBamDoubleDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getDoubleDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXDoubleDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "DBLC".equals( classCode ) ) {
								ICFBamDoubleColObj obj = (ICFBamDoubleColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getDoubleColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXDoubleColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "DBLT".equals( classCode ) ) {
								ICFBamDoubleTypeObj obj = (ICFBamDoubleTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getDoubleTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXDoubleTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "NUMD".equals( classCode ) ) {
								ICFBamNumberDefObj obj = (ICFBamNumberDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getNumberDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXNumberDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "NUMC".equals( classCode ) ) {
								ICFBamNumberColObj obj = (ICFBamNumberColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getNumberColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXNumberColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "NUMT".equals( classCode ) ) {
								ICFBamNumberTypeObj obj = (ICFBamNumberTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getNumberTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXNumberTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "STRD".equals( classCode ) ) {
								ICFBamStringDefObj obj = (ICFBamStringDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getStringDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXStringDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "STRC".equals( classCode ) ) {
								ICFBamStringColObj obj = (ICFBamStringColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getStringColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXStringColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "STRT".equals( classCode ) ) {
								ICFBamStringTypeObj obj = (ICFBamStringTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getStringTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXStringTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TXTD".equals( classCode ) ) {
								ICFBamTextDefObj obj = (ICFBamTextDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTextDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTextDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TXTC".equals( classCode ) ) {
								ICFBamTextColObj obj = (ICFBamTextColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTextColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTextColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TXTT".equals( classCode ) ) {
								ICFBamTextTypeObj obj = (ICFBamTextTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTextTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTextTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "NTKD".equals( classCode ) ) {
								ICFBamNmTokenDefObj obj = (ICFBamNmTokenDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getNmTokenDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXNmTokenDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "NTKC".equals( classCode ) ) {
								ICFBamNmTokenColObj obj = (ICFBamNmTokenColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getNmTokenColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXNmTokenColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "NTKT".equals( classCode ) ) {
								ICFBamNmTokenTypeObj obj = (ICFBamNmTokenTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getNmTokenTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXNmTokenTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "NTSD".equals( classCode ) ) {
								ICFBamNmTokensDefObj obj = (ICFBamNmTokensDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getNmTokensDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXNmTokensDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "NTSC".equals( classCode ) ) {
								ICFBamNmTokensColObj obj = (ICFBamNmTokensColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getNmTokensColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXNmTokensColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "NTST".equals( classCode ) ) {
								ICFBamNmTokensTypeObj obj = (ICFBamNmTokensTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getNmTokensTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXNmTokensTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TKND".equals( classCode ) ) {
								ICFBamTokenDefObj obj = (ICFBamTokenDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTokenDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTokenDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TKNC".equals( classCode ) ) {
								ICFBamTokenColObj obj = (ICFBamTokenColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTokenColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTokenColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TKNT".equals( classCode ) ) {
								ICFBamTokenTypeObj obj = (ICFBamTokenTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTokenTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTokenTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "DATD".equals( classCode ) ) {
								ICFBamDateDefObj obj = (ICFBamDateDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getDateDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXDateDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "DATC".equals( classCode ) ) {
								ICFBamDateColObj obj = (ICFBamDateColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getDateColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXDateColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "DATT".equals( classCode ) ) {
								ICFBamDateTypeObj obj = (ICFBamDateTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getDateTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXDateTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TIMD".equals( classCode ) ) {
								ICFBamTimeDefObj obj = (ICFBamTimeDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTimeDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTimeDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TIMC".equals( classCode ) ) {
								ICFBamTimeColObj obj = (ICFBamTimeColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTimeColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTimeColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TIMT".equals( classCode ) ) {
								ICFBamTimeTypeObj obj = (ICFBamTimeTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTimeTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTimeTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TSPD".equals( classCode ) ) {
								ICFBamTimestampDefObj obj = (ICFBamTimestampDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTimestampDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTimestampDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TSPC".equals( classCode ) ) {
								ICFBamTimestampColObj obj = (ICFBamTimestampColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTimestampColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTimestampColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TSPT".equals( classCode ) ) {
								ICFBamTimestampTypeObj obj = (ICFBamTimestampTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTimestampTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTimestampTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "DAZD".equals( classCode ) ) {
								ICFBamTZDateDefObj obj = (ICFBamTZDateDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTZDateDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTZDateDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "DAZC".equals( classCode ) ) {
								ICFBamTZDateColObj obj = (ICFBamTZDateColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTZDateColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTZDateColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "DAZT".equals( classCode ) ) {
								ICFBamTZDateTypeObj obj = (ICFBamTZDateTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTZDateTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTZDateTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TMZD".equals( classCode ) ) {
								ICFBamTZTimeDefObj obj = (ICFBamTZTimeDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTZTimeDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTZTimeDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TMZC".equals( classCode ) ) {
								ICFBamTZTimeColObj obj = (ICFBamTZTimeColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTZTimeColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTZTimeColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TMZT".equals( classCode ) ) {
								ICFBamTZTimeTypeObj obj = (ICFBamTZTimeTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTZTimeTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTZTimeTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "ZSTD".equals( classCode ) ) {
								ICFBamTZTimestampDefObj obj = (ICFBamTZTimestampDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTZTimestampDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTZTimestampDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "ZSTC".equals( classCode ) ) {
								ICFBamTZTimestampColObj obj = (ICFBamTZTimestampColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTZTimestampColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTZTimestampColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "ZSTT".equals( classCode ) ) {
								ICFBamTZTimestampTypeObj obj = (ICFBamTZTimestampTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTZTimestampTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTZTimestampTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "UIDD".equals( classCode ) ) {
								ICFBamUuidDefObj obj = (ICFBamUuidDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUuidDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXUuidDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "UIDC".equals( classCode ) ) {
								ICFBamUuidColObj obj = (ICFBamUuidColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUuidColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXUuidColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "UIDT".equals( classCode ) ) {
								ICFBamUuidTypeObj obj = (ICFBamUuidTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUuidTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXUuidTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "IGUU".equals( classCode ) ) {
								ICFBamUuidGenObj obj = (ICFBamUuidGenObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUuidGenFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXUuidGenPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFBamValueObj, ICFBamTableColObj, ICFBamAtomObj, ICFBamBlobDefObj, ICFBamBlobColObj, ICFBamBlobTypeObj, ICFBamBoolDefObj, ICFBamBoolColObj, ICFBamBoolTypeObj, ICFBamInt16DefObj, ICFBamInt16ColObj, ICFBamInt16TypeObj, ICFBamId16GenObj, ICFBamEnumDefObj, ICFBamEnumTypeObj, ICFBamInt32DefObj, ICFBamInt32ColObj, ICFBamInt32TypeObj, ICFBamId32GenObj, ICFBamInt64DefObj, ICFBamInt64ColObj, ICFBamInt64TypeObj, ICFBamId64GenObj, ICFBamUInt16DefObj, ICFBamUInt16ColObj, ICFBamUInt16TypeObj, ICFBamUInt32DefObj, ICFBamUInt32ColObj, ICFBamUInt32TypeObj, ICFBamUInt64DefObj, ICFBamUInt64ColObj, ICFBamUInt64TypeObj, ICFBamFloatDefObj, ICFBamFloatColObj, ICFBamFloatTypeObj, ICFBamDoubleDefObj, ICFBamDoubleColObj, ICFBamDoubleTypeObj, ICFBamNumberDefObj, ICFBamNumberColObj, ICFBamNumberTypeObj, ICFBamStringDefObj, ICFBamStringColObj, ICFBamStringTypeObj, ICFBamTextDefObj, ICFBamTextColObj, ICFBamTextTypeObj, ICFBamNmTokenDefObj, ICFBamNmTokenColObj, ICFBamNmTokenTypeObj, ICFBamNmTokensDefObj, ICFBamNmTokensColObj, ICFBamNmTokensTypeObj, ICFBamTokenDefObj, ICFBamTokenColObj, ICFBamTokenTypeObj, ICFBamDateDefObj, ICFBamDateColObj, ICFBamDateTypeObj, ICFBamTimeDefObj, ICFBamTimeColObj, ICFBamTimeTypeObj, ICFBamTimestampDefObj, ICFBamTimestampColObj, ICFBamTimestampTypeObj, ICFBamTZDateDefObj, ICFBamTZDateColObj, ICFBamTZDateTypeObj, ICFBamTZTimeDefObj, ICFBamTZTimeColObj, ICFBamTZTimeTypeObj, ICFBamTZTimestampDefObj, ICFBamTZTimestampColObj, ICFBamTZTimestampTypeObj, ICFBamUuidDefObj, ICFBamUuidColObj, ICFBamUuidTypeObj, ICFBamUuidGenObj" );
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonViewSelected );

			buttonEditSelected = new CFButton();
			buttonEditSelected.setMinWidth( 200 );
			buttonEditSelected.setText( "Edit Selected" );
			buttonEditSelected.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
						if( schemaObj == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"schemaObj" );
						}
						ICFBamValueObj selectedInstance = getJavaFXFocusAsValue();
						if( selectedInstance != null ) {
							String classCode = selectedInstance.getClassCode();
							if( "VALU".equals( classCode ) ) {
								CFBorderPane frame = javafxSchema.getValueFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFBamJavaFXValuePaneCommon)frame).setPaneMode( CFPane.PaneMode.Edit );
								cfFormManager.pushForm( frame );
							}
							else if( "TBLC".equals( classCode ) ) {
								ICFBamTableColObj obj = (ICFBamTableColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTableColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTableColPaneCommon)frame).setPaneMode( CFPane.PaneMode.Edit );
								cfFormManager.pushForm( frame );
							}
							else if( "ATOM".equals( classCode ) ) {
								ICFBamAtomObj obj = (ICFBamAtomObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getAtomFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXAtomPaneCommon)frame).setPaneMode( CFPane.PaneMode.Edit );
								cfFormManager.pushForm( frame );
							}
							else if( "BLBD".equals( classCode ) ) {
								ICFBamBlobDefObj obj = (ICFBamBlobDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getBlobDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXBlobDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "BLBC".equals( classCode ) ) {
								ICFBamBlobColObj obj = (ICFBamBlobColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getBlobColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXBlobColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "BLBT".equals( classCode ) ) {
								ICFBamBlobTypeObj obj = (ICFBamBlobTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getBlobTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXBlobTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "BOLD".equals( classCode ) ) {
								ICFBamBoolDefObj obj = (ICFBamBoolDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getBoolDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXBoolDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "BOLC".equals( classCode ) ) {
								ICFBamBoolColObj obj = (ICFBamBoolColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getBoolColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXBoolColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "BOLT".equals( classCode ) ) {
								ICFBamBoolTypeObj obj = (ICFBamBoolTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getBoolTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXBoolTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "I16D".equals( classCode ) ) {
								ICFBamInt16DefObj obj = (ICFBamInt16DefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getInt16DefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXInt16DefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "I16C".equals( classCode ) ) {
								ICFBamInt16ColObj obj = (ICFBamInt16ColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getInt16ColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXInt16ColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "I16T".equals( classCode ) ) {
								ICFBamInt16TypeObj obj = (ICFBamInt16TypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getInt16TypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXInt16TypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "IG16".equals( classCode ) ) {
								ICFBamId16GenObj obj = (ICFBamId16GenObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getId16GenFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXId16GenPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "ENMD".equals( classCode ) ) {
								ICFBamEnumDefObj obj = (ICFBamEnumDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getEnumDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXEnumDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "ENMT".equals( classCode ) ) {
								ICFBamEnumTypeObj obj = (ICFBamEnumTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getEnumTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXEnumTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "I32D".equals( classCode ) ) {
								ICFBamInt32DefObj obj = (ICFBamInt32DefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getInt32DefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXInt32DefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "I32C".equals( classCode ) ) {
								ICFBamInt32ColObj obj = (ICFBamInt32ColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getInt32ColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXInt32ColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "I32T".equals( classCode ) ) {
								ICFBamInt32TypeObj obj = (ICFBamInt32TypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getInt32TypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXInt32TypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "IG32".equals( classCode ) ) {
								ICFBamId32GenObj obj = (ICFBamId32GenObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getId32GenFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXId32GenPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "I64D".equals( classCode ) ) {
								ICFBamInt64DefObj obj = (ICFBamInt64DefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getInt64DefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXInt64DefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "I64C".equals( classCode ) ) {
								ICFBamInt64ColObj obj = (ICFBamInt64ColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getInt64ColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXInt64ColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "I64T".equals( classCode ) ) {
								ICFBamInt64TypeObj obj = (ICFBamInt64TypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getInt64TypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXInt64TypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "IG64".equals( classCode ) ) {
								ICFBamId64GenObj obj = (ICFBamId64GenObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getId64GenFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXId64GenPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "U16D".equals( classCode ) ) {
								ICFBamUInt16DefObj obj = (ICFBamUInt16DefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUInt16DefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXUInt16DefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "U16C".equals( classCode ) ) {
								ICFBamUInt16ColObj obj = (ICFBamUInt16ColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUInt16ColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXUInt16ColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "U16T".equals( classCode ) ) {
								ICFBamUInt16TypeObj obj = (ICFBamUInt16TypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUInt16TypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXUInt16TypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "U32D".equals( classCode ) ) {
								ICFBamUInt32DefObj obj = (ICFBamUInt32DefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUInt32DefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXUInt32DefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "U32C".equals( classCode ) ) {
								ICFBamUInt32ColObj obj = (ICFBamUInt32ColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUInt32ColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXUInt32ColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "U32T".equals( classCode ) ) {
								ICFBamUInt32TypeObj obj = (ICFBamUInt32TypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUInt32TypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXUInt32TypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "U64D".equals( classCode ) ) {
								ICFBamUInt64DefObj obj = (ICFBamUInt64DefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUInt64DefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXUInt64DefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "U64C".equals( classCode ) ) {
								ICFBamUInt64ColObj obj = (ICFBamUInt64ColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUInt64ColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXUInt64ColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "U64T".equals( classCode ) ) {
								ICFBamUInt64TypeObj obj = (ICFBamUInt64TypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUInt64TypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXUInt64TypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "FLTD".equals( classCode ) ) {
								ICFBamFloatDefObj obj = (ICFBamFloatDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getFloatDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXFloatDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "FLTC".equals( classCode ) ) {
								ICFBamFloatColObj obj = (ICFBamFloatColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getFloatColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXFloatColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "FLTT".equals( classCode ) ) {
								ICFBamFloatTypeObj obj = (ICFBamFloatTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getFloatTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXFloatTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "DBLD".equals( classCode ) ) {
								ICFBamDoubleDefObj obj = (ICFBamDoubleDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getDoubleDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXDoubleDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "DBLC".equals( classCode ) ) {
								ICFBamDoubleColObj obj = (ICFBamDoubleColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getDoubleColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXDoubleColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "DBLT".equals( classCode ) ) {
								ICFBamDoubleTypeObj obj = (ICFBamDoubleTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getDoubleTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXDoubleTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "NUMD".equals( classCode ) ) {
								ICFBamNumberDefObj obj = (ICFBamNumberDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getNumberDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXNumberDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "NUMC".equals( classCode ) ) {
								ICFBamNumberColObj obj = (ICFBamNumberColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getNumberColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXNumberColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "NUMT".equals( classCode ) ) {
								ICFBamNumberTypeObj obj = (ICFBamNumberTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getNumberTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXNumberTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "STRD".equals( classCode ) ) {
								ICFBamStringDefObj obj = (ICFBamStringDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getStringDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXStringDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "STRC".equals( classCode ) ) {
								ICFBamStringColObj obj = (ICFBamStringColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getStringColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXStringColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "STRT".equals( classCode ) ) {
								ICFBamStringTypeObj obj = (ICFBamStringTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getStringTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXStringTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TXTD".equals( classCode ) ) {
								ICFBamTextDefObj obj = (ICFBamTextDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTextDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTextDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TXTC".equals( classCode ) ) {
								ICFBamTextColObj obj = (ICFBamTextColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTextColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTextColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TXTT".equals( classCode ) ) {
								ICFBamTextTypeObj obj = (ICFBamTextTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTextTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTextTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "NTKD".equals( classCode ) ) {
								ICFBamNmTokenDefObj obj = (ICFBamNmTokenDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getNmTokenDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXNmTokenDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "NTKC".equals( classCode ) ) {
								ICFBamNmTokenColObj obj = (ICFBamNmTokenColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getNmTokenColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXNmTokenColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "NTKT".equals( classCode ) ) {
								ICFBamNmTokenTypeObj obj = (ICFBamNmTokenTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getNmTokenTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXNmTokenTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "NTSD".equals( classCode ) ) {
								ICFBamNmTokensDefObj obj = (ICFBamNmTokensDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getNmTokensDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXNmTokensDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "NTSC".equals( classCode ) ) {
								ICFBamNmTokensColObj obj = (ICFBamNmTokensColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getNmTokensColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXNmTokensColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "NTST".equals( classCode ) ) {
								ICFBamNmTokensTypeObj obj = (ICFBamNmTokensTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getNmTokensTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXNmTokensTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TKND".equals( classCode ) ) {
								ICFBamTokenDefObj obj = (ICFBamTokenDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTokenDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTokenDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TKNC".equals( classCode ) ) {
								ICFBamTokenColObj obj = (ICFBamTokenColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTokenColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTokenColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TKNT".equals( classCode ) ) {
								ICFBamTokenTypeObj obj = (ICFBamTokenTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTokenTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTokenTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "DATD".equals( classCode ) ) {
								ICFBamDateDefObj obj = (ICFBamDateDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getDateDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXDateDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "DATC".equals( classCode ) ) {
								ICFBamDateColObj obj = (ICFBamDateColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getDateColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXDateColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "DATT".equals( classCode ) ) {
								ICFBamDateTypeObj obj = (ICFBamDateTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getDateTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXDateTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TIMD".equals( classCode ) ) {
								ICFBamTimeDefObj obj = (ICFBamTimeDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTimeDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTimeDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TIMC".equals( classCode ) ) {
								ICFBamTimeColObj obj = (ICFBamTimeColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTimeColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTimeColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TIMT".equals( classCode ) ) {
								ICFBamTimeTypeObj obj = (ICFBamTimeTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTimeTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTimeTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TSPD".equals( classCode ) ) {
								ICFBamTimestampDefObj obj = (ICFBamTimestampDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTimestampDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTimestampDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TSPC".equals( classCode ) ) {
								ICFBamTimestampColObj obj = (ICFBamTimestampColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTimestampColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTimestampColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TSPT".equals( classCode ) ) {
								ICFBamTimestampTypeObj obj = (ICFBamTimestampTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTimestampTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTimestampTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "DAZD".equals( classCode ) ) {
								ICFBamTZDateDefObj obj = (ICFBamTZDateDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTZDateDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTZDateDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "DAZC".equals( classCode ) ) {
								ICFBamTZDateColObj obj = (ICFBamTZDateColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTZDateColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTZDateColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "DAZT".equals( classCode ) ) {
								ICFBamTZDateTypeObj obj = (ICFBamTZDateTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTZDateTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTZDateTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TMZD".equals( classCode ) ) {
								ICFBamTZTimeDefObj obj = (ICFBamTZTimeDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTZTimeDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTZTimeDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TMZC".equals( classCode ) ) {
								ICFBamTZTimeColObj obj = (ICFBamTZTimeColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTZTimeColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTZTimeColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TMZT".equals( classCode ) ) {
								ICFBamTZTimeTypeObj obj = (ICFBamTZTimeTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTZTimeTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTZTimeTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "ZSTD".equals( classCode ) ) {
								ICFBamTZTimestampDefObj obj = (ICFBamTZTimestampDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTZTimestampDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTZTimestampDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "ZSTC".equals( classCode ) ) {
								ICFBamTZTimestampColObj obj = (ICFBamTZTimestampColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTZTimestampColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTZTimestampColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "ZSTT".equals( classCode ) ) {
								ICFBamTZTimestampTypeObj obj = (ICFBamTZTimestampTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTZTimestampTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTZTimestampTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "UIDD".equals( classCode ) ) {
								ICFBamUuidDefObj obj = (ICFBamUuidDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUuidDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXUuidDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "UIDC".equals( classCode ) ) {
								ICFBamUuidColObj obj = (ICFBamUuidColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUuidColFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXUuidColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "UIDT".equals( classCode ) ) {
								ICFBamUuidTypeObj obj = (ICFBamUuidTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUuidTypeFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXUuidTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "IGUU".equals( classCode ) ) {
								ICFBamUuidGenObj obj = (ICFBamUuidGenObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUuidGenFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXUuidGenPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFBamValueObj, ICFBamTableColObj, ICFBamAtomObj, ICFBamBlobDefObj, ICFBamBlobColObj, ICFBamBlobTypeObj, ICFBamBoolDefObj, ICFBamBoolColObj, ICFBamBoolTypeObj, ICFBamInt16DefObj, ICFBamInt16ColObj, ICFBamInt16TypeObj, ICFBamId16GenObj, ICFBamEnumDefObj, ICFBamEnumTypeObj, ICFBamInt32DefObj, ICFBamInt32ColObj, ICFBamInt32TypeObj, ICFBamId32GenObj, ICFBamInt64DefObj, ICFBamInt64ColObj, ICFBamInt64TypeObj, ICFBamId64GenObj, ICFBamUInt16DefObj, ICFBamUInt16ColObj, ICFBamUInt16TypeObj, ICFBamUInt32DefObj, ICFBamUInt32ColObj, ICFBamUInt32TypeObj, ICFBamUInt64DefObj, ICFBamUInt64ColObj, ICFBamUInt64TypeObj, ICFBamFloatDefObj, ICFBamFloatColObj, ICFBamFloatTypeObj, ICFBamDoubleDefObj, ICFBamDoubleColObj, ICFBamDoubleTypeObj, ICFBamNumberDefObj, ICFBamNumberColObj, ICFBamNumberTypeObj, ICFBamStringDefObj, ICFBamStringColObj, ICFBamStringTypeObj, ICFBamTextDefObj, ICFBamTextColObj, ICFBamTextTypeObj, ICFBamNmTokenDefObj, ICFBamNmTokenColObj, ICFBamNmTokenTypeObj, ICFBamNmTokensDefObj, ICFBamNmTokensColObj, ICFBamNmTokensTypeObj, ICFBamTokenDefObj, ICFBamTokenColObj, ICFBamTokenTypeObj, ICFBamDateDefObj, ICFBamDateColObj, ICFBamDateTypeObj, ICFBamTimeDefObj, ICFBamTimeColObj, ICFBamTimeTypeObj, ICFBamTimestampDefObj, ICFBamTimestampColObj, ICFBamTimestampTypeObj, ICFBamTZDateDefObj, ICFBamTZDateColObj, ICFBamTZDateTypeObj, ICFBamTZTimeDefObj, ICFBamTZTimeColObj, ICFBamTZTimeTypeObj, ICFBamTZTimestampDefObj, ICFBamTZTimestampColObj, ICFBamTZTimestampTypeObj, ICFBamUuidDefObj, ICFBamUuidColObj, ICFBamUuidTypeObj, ICFBamUuidGenObj" );
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			if( ( javafxContainer instanceof ICFBamSchemaDefObj )
					|| ( ( javafxContainer instanceof ICFBamTableObj )
						&& ( ((ICFBamTableObj)javafxContainer).getOptionalLookupDefSchema() == null ) ) )
				{
					hboxMenu.getChildren().add( buttonEditSelected );
				}

			buttonDeleteSelected = new CFButton();
			buttonDeleteSelected.setMinWidth( 200 );
			buttonDeleteSelected.setText( "Delete Selected" );
			buttonDeleteSelected.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
						if( schemaObj == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"schemaObj" );
						}
						ICFBamValueObj selectedInstance = getJavaFXFocusAsValue();
						if( selectedInstance != null ) {
							String classCode = selectedInstance.getClassCode();
							if( "VALU".equals( classCode ) ) {
								CFBorderPane frame = javafxSchema.getValueFactory().newAskDeleteForm( cfFormManager, selectedInstance, getDeleteCallback() );
								((ICFBamJavaFXValuePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TBLC".equals( classCode ) ) {
								ICFBamTableColObj obj = (ICFBamTableColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTableColFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXTableColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "ATOM".equals( classCode ) ) {
								ICFBamAtomObj obj = (ICFBamAtomObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getAtomFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXAtomPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "BLBD".equals( classCode ) ) {
								ICFBamBlobDefObj obj = (ICFBamBlobDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getBlobDefFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXBlobDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "BLBC".equals( classCode ) ) {
								ICFBamBlobColObj obj = (ICFBamBlobColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getBlobColFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXBlobColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "BLBT".equals( classCode ) ) {
								ICFBamBlobTypeObj obj = (ICFBamBlobTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getBlobTypeFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXBlobTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "BOLD".equals( classCode ) ) {
								ICFBamBoolDefObj obj = (ICFBamBoolDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getBoolDefFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXBoolDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "BOLC".equals( classCode ) ) {
								ICFBamBoolColObj obj = (ICFBamBoolColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getBoolColFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXBoolColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "BOLT".equals( classCode ) ) {
								ICFBamBoolTypeObj obj = (ICFBamBoolTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getBoolTypeFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXBoolTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "I16D".equals( classCode ) ) {
								ICFBamInt16DefObj obj = (ICFBamInt16DefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getInt16DefFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXInt16DefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "I16C".equals( classCode ) ) {
								ICFBamInt16ColObj obj = (ICFBamInt16ColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getInt16ColFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXInt16ColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "I16T".equals( classCode ) ) {
								ICFBamInt16TypeObj obj = (ICFBamInt16TypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getInt16TypeFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXInt16TypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "IG16".equals( classCode ) ) {
								ICFBamId16GenObj obj = (ICFBamId16GenObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getId16GenFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXId16GenPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "ENMD".equals( classCode ) ) {
								ICFBamEnumDefObj obj = (ICFBamEnumDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getEnumDefFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXEnumDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "ENMT".equals( classCode ) ) {
								ICFBamEnumTypeObj obj = (ICFBamEnumTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getEnumTypeFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXEnumTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "I32D".equals( classCode ) ) {
								ICFBamInt32DefObj obj = (ICFBamInt32DefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getInt32DefFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXInt32DefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "I32C".equals( classCode ) ) {
								ICFBamInt32ColObj obj = (ICFBamInt32ColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getInt32ColFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXInt32ColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "I32T".equals( classCode ) ) {
								ICFBamInt32TypeObj obj = (ICFBamInt32TypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getInt32TypeFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXInt32TypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "IG32".equals( classCode ) ) {
								ICFBamId32GenObj obj = (ICFBamId32GenObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getId32GenFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXId32GenPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "I64D".equals( classCode ) ) {
								ICFBamInt64DefObj obj = (ICFBamInt64DefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getInt64DefFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXInt64DefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "I64C".equals( classCode ) ) {
								ICFBamInt64ColObj obj = (ICFBamInt64ColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getInt64ColFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXInt64ColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "I64T".equals( classCode ) ) {
								ICFBamInt64TypeObj obj = (ICFBamInt64TypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getInt64TypeFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXInt64TypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "IG64".equals( classCode ) ) {
								ICFBamId64GenObj obj = (ICFBamId64GenObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getId64GenFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXId64GenPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "U16D".equals( classCode ) ) {
								ICFBamUInt16DefObj obj = (ICFBamUInt16DefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUInt16DefFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXUInt16DefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "U16C".equals( classCode ) ) {
								ICFBamUInt16ColObj obj = (ICFBamUInt16ColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUInt16ColFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXUInt16ColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "U16T".equals( classCode ) ) {
								ICFBamUInt16TypeObj obj = (ICFBamUInt16TypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUInt16TypeFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXUInt16TypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "U32D".equals( classCode ) ) {
								ICFBamUInt32DefObj obj = (ICFBamUInt32DefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUInt32DefFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXUInt32DefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "U32C".equals( classCode ) ) {
								ICFBamUInt32ColObj obj = (ICFBamUInt32ColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUInt32ColFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXUInt32ColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "U32T".equals( classCode ) ) {
								ICFBamUInt32TypeObj obj = (ICFBamUInt32TypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUInt32TypeFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXUInt32TypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "U64D".equals( classCode ) ) {
								ICFBamUInt64DefObj obj = (ICFBamUInt64DefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUInt64DefFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXUInt64DefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "U64C".equals( classCode ) ) {
								ICFBamUInt64ColObj obj = (ICFBamUInt64ColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUInt64ColFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXUInt64ColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "U64T".equals( classCode ) ) {
								ICFBamUInt64TypeObj obj = (ICFBamUInt64TypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUInt64TypeFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXUInt64TypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "FLTD".equals( classCode ) ) {
								ICFBamFloatDefObj obj = (ICFBamFloatDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getFloatDefFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXFloatDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "FLTC".equals( classCode ) ) {
								ICFBamFloatColObj obj = (ICFBamFloatColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getFloatColFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXFloatColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "FLTT".equals( classCode ) ) {
								ICFBamFloatTypeObj obj = (ICFBamFloatTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getFloatTypeFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXFloatTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "DBLD".equals( classCode ) ) {
								ICFBamDoubleDefObj obj = (ICFBamDoubleDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getDoubleDefFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXDoubleDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "DBLC".equals( classCode ) ) {
								ICFBamDoubleColObj obj = (ICFBamDoubleColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getDoubleColFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXDoubleColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "DBLT".equals( classCode ) ) {
								ICFBamDoubleTypeObj obj = (ICFBamDoubleTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getDoubleTypeFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXDoubleTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "NUMD".equals( classCode ) ) {
								ICFBamNumberDefObj obj = (ICFBamNumberDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getNumberDefFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXNumberDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "NUMC".equals( classCode ) ) {
								ICFBamNumberColObj obj = (ICFBamNumberColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getNumberColFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXNumberColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "NUMT".equals( classCode ) ) {
								ICFBamNumberTypeObj obj = (ICFBamNumberTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getNumberTypeFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXNumberTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "STRD".equals( classCode ) ) {
								ICFBamStringDefObj obj = (ICFBamStringDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getStringDefFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXStringDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "STRC".equals( classCode ) ) {
								ICFBamStringColObj obj = (ICFBamStringColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getStringColFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXStringColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "STRT".equals( classCode ) ) {
								ICFBamStringTypeObj obj = (ICFBamStringTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getStringTypeFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXStringTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TXTD".equals( classCode ) ) {
								ICFBamTextDefObj obj = (ICFBamTextDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTextDefFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXTextDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TXTC".equals( classCode ) ) {
								ICFBamTextColObj obj = (ICFBamTextColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTextColFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXTextColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TXTT".equals( classCode ) ) {
								ICFBamTextTypeObj obj = (ICFBamTextTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTextTypeFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXTextTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "NTKD".equals( classCode ) ) {
								ICFBamNmTokenDefObj obj = (ICFBamNmTokenDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getNmTokenDefFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXNmTokenDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "NTKC".equals( classCode ) ) {
								ICFBamNmTokenColObj obj = (ICFBamNmTokenColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getNmTokenColFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXNmTokenColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "NTKT".equals( classCode ) ) {
								ICFBamNmTokenTypeObj obj = (ICFBamNmTokenTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getNmTokenTypeFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXNmTokenTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "NTSD".equals( classCode ) ) {
								ICFBamNmTokensDefObj obj = (ICFBamNmTokensDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getNmTokensDefFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXNmTokensDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "NTSC".equals( classCode ) ) {
								ICFBamNmTokensColObj obj = (ICFBamNmTokensColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getNmTokensColFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXNmTokensColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "NTST".equals( classCode ) ) {
								ICFBamNmTokensTypeObj obj = (ICFBamNmTokensTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getNmTokensTypeFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXNmTokensTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TKND".equals( classCode ) ) {
								ICFBamTokenDefObj obj = (ICFBamTokenDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTokenDefFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXTokenDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TKNC".equals( classCode ) ) {
								ICFBamTokenColObj obj = (ICFBamTokenColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTokenColFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXTokenColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TKNT".equals( classCode ) ) {
								ICFBamTokenTypeObj obj = (ICFBamTokenTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTokenTypeFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXTokenTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "DATD".equals( classCode ) ) {
								ICFBamDateDefObj obj = (ICFBamDateDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getDateDefFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXDateDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "DATC".equals( classCode ) ) {
								ICFBamDateColObj obj = (ICFBamDateColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getDateColFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXDateColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "DATT".equals( classCode ) ) {
								ICFBamDateTypeObj obj = (ICFBamDateTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getDateTypeFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXDateTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TIMD".equals( classCode ) ) {
								ICFBamTimeDefObj obj = (ICFBamTimeDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTimeDefFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXTimeDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TIMC".equals( classCode ) ) {
								ICFBamTimeColObj obj = (ICFBamTimeColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTimeColFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXTimeColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TIMT".equals( classCode ) ) {
								ICFBamTimeTypeObj obj = (ICFBamTimeTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTimeTypeFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXTimeTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TSPD".equals( classCode ) ) {
								ICFBamTimestampDefObj obj = (ICFBamTimestampDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTimestampDefFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXTimestampDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TSPC".equals( classCode ) ) {
								ICFBamTimestampColObj obj = (ICFBamTimestampColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTimestampColFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXTimestampColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TSPT".equals( classCode ) ) {
								ICFBamTimestampTypeObj obj = (ICFBamTimestampTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTimestampTypeFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXTimestampTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "DAZD".equals( classCode ) ) {
								ICFBamTZDateDefObj obj = (ICFBamTZDateDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTZDateDefFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXTZDateDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "DAZC".equals( classCode ) ) {
								ICFBamTZDateColObj obj = (ICFBamTZDateColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTZDateColFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXTZDateColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "DAZT".equals( classCode ) ) {
								ICFBamTZDateTypeObj obj = (ICFBamTZDateTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTZDateTypeFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXTZDateTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TMZD".equals( classCode ) ) {
								ICFBamTZTimeDefObj obj = (ICFBamTZTimeDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTZTimeDefFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXTZTimeDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TMZC".equals( classCode ) ) {
								ICFBamTZTimeColObj obj = (ICFBamTZTimeColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTZTimeColFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXTZTimeColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "TMZT".equals( classCode ) ) {
								ICFBamTZTimeTypeObj obj = (ICFBamTZTimeTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTZTimeTypeFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXTZTimeTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "ZSTD".equals( classCode ) ) {
								ICFBamTZTimestampDefObj obj = (ICFBamTZTimestampDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTZTimestampDefFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXTZTimestampDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "ZSTC".equals( classCode ) ) {
								ICFBamTZTimestampColObj obj = (ICFBamTZTimestampColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTZTimestampColFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXTZTimestampColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "ZSTT".equals( classCode ) ) {
								ICFBamTZTimestampTypeObj obj = (ICFBamTZTimestampTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTZTimestampTypeFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXTZTimestampTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "UIDD".equals( classCode ) ) {
								ICFBamUuidDefObj obj = (ICFBamUuidDefObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUuidDefFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXUuidDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "UIDC".equals( classCode ) ) {
								ICFBamUuidColObj obj = (ICFBamUuidColObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUuidColFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXUuidColPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "UIDT".equals( classCode ) ) {
								ICFBamUuidTypeObj obj = (ICFBamUuidTypeObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUuidTypeFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXUuidTypePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( "IGUU".equals( classCode ) ) {
								ICFBamUuidGenObj obj = (ICFBamUuidGenObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getUuidGenFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXUuidGenPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFBamValueObj, ICFBamTableColObj, ICFBamAtomObj, ICFBamBlobDefObj, ICFBamBlobColObj, ICFBamBlobTypeObj, ICFBamBoolDefObj, ICFBamBoolColObj, ICFBamBoolTypeObj, ICFBamInt16DefObj, ICFBamInt16ColObj, ICFBamInt16TypeObj, ICFBamId16GenObj, ICFBamEnumDefObj, ICFBamEnumTypeObj, ICFBamInt32DefObj, ICFBamInt32ColObj, ICFBamInt32TypeObj, ICFBamId32GenObj, ICFBamInt64DefObj, ICFBamInt64ColObj, ICFBamInt64TypeObj, ICFBamId64GenObj, ICFBamUInt16DefObj, ICFBamUInt16ColObj, ICFBamUInt16TypeObj, ICFBamUInt32DefObj, ICFBamUInt32ColObj, ICFBamUInt32TypeObj, ICFBamUInt64DefObj, ICFBamUInt64ColObj, ICFBamUInt64TypeObj, ICFBamFloatDefObj, ICFBamFloatColObj, ICFBamFloatTypeObj, ICFBamDoubleDefObj, ICFBamDoubleColObj, ICFBamDoubleTypeObj, ICFBamNumberDefObj, ICFBamNumberColObj, ICFBamNumberTypeObj, ICFBamStringDefObj, ICFBamStringColObj, ICFBamStringTypeObj, ICFBamTextDefObj, ICFBamTextColObj, ICFBamTextTypeObj, ICFBamNmTokenDefObj, ICFBamNmTokenColObj, ICFBamNmTokenTypeObj, ICFBamNmTokensDefObj, ICFBamNmTokensColObj, ICFBamNmTokensTypeObj, ICFBamTokenDefObj, ICFBamTokenColObj, ICFBamTokenTypeObj, ICFBamDateDefObj, ICFBamDateColObj, ICFBamDateTypeObj, ICFBamTimeDefObj, ICFBamTimeColObj, ICFBamTimeTypeObj, ICFBamTimestampDefObj, ICFBamTimestampColObj, ICFBamTimestampTypeObj, ICFBamTZDateDefObj, ICFBamTZDateColObj, ICFBamTZDateTypeObj, ICFBamTZTimeDefObj, ICFBamTZTimeColObj, ICFBamTZTimeTypeObj, ICFBamTZTimestampDefObj, ICFBamTZTimestampColObj, ICFBamTZTimestampTypeObj, ICFBamUuidDefObj, ICFBamUuidColObj, ICFBamUuidTypeObj, ICFBamUuidGenObj" );
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			if( ( javafxContainer instanceof ICFBamSchemaDefObj )
					|| ( ( javafxContainer instanceof ICFBamTableObj )
						&& ( ((ICFBamTableObj)javafxContainer).getOptionalLookupDefSchema() == null ) ) )
				{
					hboxMenu.getChildren().add( buttonDeleteSelected );
				}

		}
		return( hboxMenu );
	}

	public ICFBamScopeObj getJavaFXContainer() {
		return( javafxContainer );
	}

	public void setJavaFXContainer( ICFBamScopeObj value ) {
		javafxContainer = value;
	}

	public void refreshMe() {
		if( javafxRefreshCallback != null ) {
			javafxRefreshCallback.refreshMe();
		}
	}

	public void adjustListButtons() {
		boolean enableState;
		boolean inEditState;
		boolean allowAdds;
		boolean inAddMode = false;
		ICFBamValueObj selectedObj = getJavaFXFocusAsValue();
		CFPane.PaneMode mode = getPaneMode();
		if( mode == CFPane.PaneMode.Edit ) {
			inEditState = true;
			allowAdds = false;
		}
		else {
			inEditState = false;
			if( getJavaFXContainer() != null ) {
				if( getLeft() != null ) {
					allowAdds = false;
					inAddMode = true;
				}
				if( ( javafxContainer instanceof ICFBamTableObj )
					&& ( ((ICFBamTableObj)javafxContainer).getOptionalLookupDefSchema() != null ) )
				{
					allowAdds = false;
				}
				else {
					allowAdds = true;
				}
			}
			else {
				allowAdds = false;
			}
		}
		if( selectedObj == null ) {
			enableState = false;
		}
		else {
			if( ( ! inAddMode ) && ( ! inEditState ) ) {
				enableState = true;
			}
			else {
				enableState = false;
			}
		}

		boolean enableMoveUp = ( ! inEditState )
			&& ( ! inAddMode )
			&& ( selectedObj != null )
			&& ( null != selectedObj.getOptionalLookupPrev() );
		if( enableMoveUp ) {
			if( selectedObj.getOptionalLookupPrev().getOptionalLookupDefSchema() != null ) {
				enableMoveUp = false;
			}
		}
		boolean enableMoveDown = ( ! inEditState )
			&& ( ! inAddMode )
			&& ( selectedObj != null )
			&& ( null != selectedObj.getOptionalLookupNext() );
		if( buttonMoveUpSelected != null ) {
			if( inEditState ) {
				buttonMoveUpSelected.setDisable( true );
			}
			else if( ( selectedObj != null ) && ( selectedObj.getOptionalLookupDefSchema() == null ) ) {
				buttonMoveUpSelected.setDisable( ! enableMoveUp );
			}
			else {
				buttonMoveUpSelected.setDisable( true );
			}
		}
		if( buttonMoveDownSelected != null ) {
			if( inEditState ) {
				buttonMoveDownSelected.setDisable( true );
			}
			else if( ( selectedObj != null ) && ( selectedObj.getOptionalLookupDefSchema() == null ) ) {
				buttonMoveDownSelected.setDisable( ! enableMoveDown );
			}
			else {
				buttonMoveDownSelected.setDisable( true );
			}
		}
		if( buttonViewSelected != null ) {
			if( inEditState ) {
				buttonViewSelected.setDisable( true );
			}
			else {
				buttonViewSelected.setDisable( ! enableState );
			}
		}
		if( buttonEditSelected != null ) {
			if( inEditState ) {
				buttonEditSelected.setDisable( true );
			}
			else if( ( selectedObj != null ) && ( selectedObj.getOptionalLookupDefSchema() == null ) ) {
				buttonEditSelected.setDisable( ! enableState );
			}
			else {
				buttonEditSelected.setDisable( true );
			}
		}
		if( buttonDeleteSelected != null ) {
			if( inEditState ) {
				buttonDeleteSelected.setDisable( true );
			}
			else if( ( selectedObj != null ) && ( selectedObj.getOptionalLookupDefSchema() == null ) ) {
				buttonDeleteSelected.setDisable( ! enableState );
			}
			else {
				buttonDeleteSelected.setDisable( true );
			}
		}

		if( buttonAdd != null ) {
			buttonAdd.setDisable( ! allowAdds );
		}
	}
}
