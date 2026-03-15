// Description: Java 25 Custom JavaFX Schema.

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

import java.io.*;
import java.nio.*;
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
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellDataFeatures;
import javafx.scene.control.TreeTableView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class CFBamCustEditorHierarchyPane
extends CFBorderPane
implements ICFForm
{
	protected final String S_FormName = "Network Hierarchy";
	protected ICFFormManager cfFormManager = null;
	protected ICFBamCustEditorSchema customSchema = null;
	protected String modelName = null;
	private static ICFLibMessageLog	log = new ConsoleLogWrapper();
	private static MSSBamCFPrefs userPrefs = null;
	private static MSSBamCFEngine cfEngine = null;
	protected ICFBamMinorVersionObj definedVersion = null;
	protected CFLabel labelTitle = null;
	protected CFBorderPane borderSub = null;
	protected FileChooser fileChooser = null;

	public static MSSBamCFEngine getCFEngine() {
		return( cfEngine );
	}

	public static MSSBamCFPrefs getCFPrefs() {
		return( userPrefs );
	}

	public static class ConsoleLogWrapper
	implements ICFLibMessageLog
	{
		private int		   	indent = 0;

		public ConsoleLogWrapper() {
			indent = 0;
		}

		@Override
		public int getMessageLogIndent() {
			return( indent );
		}

		@Override
		public synchronized void dedent() {
			if( indent > 0 ) {
				indent --;
			}
			else {
				indent = 0;
			}
		}

		@Override
		public synchronized void indent() {
			indent ++;
		}

		@Override
		public PrintStream getPrintStream() {
			return( null );
		}

		@Override
		public synchronized void message( String msg ) {
			if( msg == null ) {
				return;
			}

			StringBuffer buff = new StringBuffer();

			int			i;
			for( i = 0; i < indent; i ++ ) {
				buff.append( "\t" );
			}
			buff.append( msg );

			CFConsole.message( buff.toString() );
			if( ! msg.endsWith( "\n" ) ) {
				buff.append( "\n" );
			}
			System.out.append( buff.toString() );
		}

		public String getBacklog() {
			final String retval = "";
			return( retval );
		}

		@Override
		public void openLogFile( String fileName )
			throws FileNotFoundException
		{
		}

		@Override
		public void closeLogFile() {
		}
	}

	public class CFBamCustHierarchyItem
	extends TreeItem<CFBamCustHierarchyNode>
	{
		public CFBamCustHierarchyItem() {
			super();
		}
	}

	public class CFBamCustHierarchyNode
	implements ICFLibAnyObj
	{
		protected ICFLibAnyObj obj = null;

		public CFBamCustHierarchyNode() {
		}

		public ICFLibAnyObj getObj() {
			return( obj );
		}

		public void setObj( ICFLibAnyObj value ) {
			final String S_ProcName = "setObj";
			if( value == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					1,
					"value" );
			}
			if( value instanceof ICFBamTldObj ) {
				obj = value;
			}
			else if( value instanceof ICFBamTopDomainObj ) {
				obj = value;
			}
			else if( value instanceof ICFBamTopProjectObj ) {
				obj = value;
			}
			else if( value instanceof ICFBamSubProjectObj ) {
				obj = value;
			}
			else if( value instanceof ICFBamMajorVersionObj ) {
				obj = value;
			}
			else if( value instanceof ICFBamMinorVersionObj ) {
				obj = value;
			}
			else if( value instanceof ICFBamLicenseObj ) {
				obj = value;
			}
			else if( value instanceof ICFBamSchemaDefObj ) {
				obj = value;
			}
			else {
				throw new CFLibUnsupportedClassException( getClass(),
					S_ProcName,
					"value",
					value,
					"ICFBamTldObj, ICFBamTopDomainObj, ICFBamDomainObj, ICFBamTopProjectObj, ICFBamSubProjectObj, ICFBamMajorVersionObj, ICFBamMinorVersionObj, ICFBamLicenseObj, ICFBamSchemaDefObj" );
			}
		}

		public ICFBamTldObj getObjAsTld() {
			ICFBamTldObj retval = (ICFBamTldObj)obj;
			return( retval );
		}

		public ICFBamLicenseObj getObjAsLicense() {
			ICFBamLicenseObj retval = (ICFBamLicenseObj)obj;
			return( retval );
		}

		public ICFBamSchemaDefObj getObjAsSchemaDef() {
			ICFBamSchemaDefObj retval = (ICFBamSchemaDefObj)obj;
			return( retval );
		}

		@Override
		public String getGenDefName() {
			String retval;
			if( obj != null ) {
				retval = obj.getGenDefName();
			}
			else {
				retval = "Object";
			}
			return( retval );
		}

		@Override
		public ICFLibAnyObj getObjScope() {
			ICFLibAnyObj retval;
			if( obj != null ) {
				retval = obj.getObjScope();
			}
			else {
				retval = null;
			}
			return( retval );
		}

		@Override
		public String getObjName() {
			String retval;
			if( obj != null ) {
				retval = obj.getObjName();
			}
			else {
				retval = ".";
			}
			return( retval );
		}

		@Override
		public String getObjQualifiedName() {
			String retval;
			if( obj != null ) {
				retval = obj.getObjQualifiedName();
			}
			else {
				retval = ".";
			}
			return( retval );
		}

		@Override
		public String getObjFullName() {
			String retval;
			if( obj != null ) {
				retval = obj.getObjFullName();
			}
			else {
				retval = ".";
			}
			return( retval );
		}

		@Override
		public ICFLibAnyObj getNamedObject(Class qualifyingClass, String objName) {
			ICFLibAnyObj retval;
			if( obj != null ) {
				retval = obj.getNamedObject( qualifyingClass, objName );
			}
			else {
				retval = null;
			}
			return( retval );
		}

		@Override
		public ICFLibAnyObj getNamedObject(String objName) {
			ICFLibAnyObj retval;
			if( obj != null ) {
				retval = obj.getNamedObject( objName );
			}
			else {
				retval = null;
			}
			return( retval );
		}

		@Override
		public ICFLibAnyObj getObjQualifier(Class qualifyingClass) {
			ICFLibAnyObj retval;
			if( obj != null ) {
				retval = obj.getObjQualifier( qualifyingClass );
			}
			else {
				retval = null;
			}
			return( retval );
		}

		public ICFBamTldObj getOptionalContainerTld() {
			ICFBamTldObj retval;
			if( obj == null ) {
				retval = null;
			}
			else if( obj instanceof ICFBamTopDomainObj ) {
				retval = (ICFBamTldObj) ((ICFBamTopDomainObj)obj).getRequiredContainerParentTld();
			}
			else {
				retval = null;
			}
			return( retval );
		}

		public ICFBamTldObj getOptionalContainerTld( boolean forceRead ) {
			ICFBamTldObj retval;
			if( obj == null ) {
				retval = null;
			}
			else if( obj instanceof ICFBamTopDomainObj ) {
				retval = (ICFBamTldObj) ((ICFBamTopDomainObj)obj).getRequiredContainerParentTld( forceRead );
			}
			else {
				retval = null;
			}
			return( retval );
		}

		public ICFBamTopDomainObj getOptionalContainerParentTopDomain() {
			ICFBamTopDomainObj retval;
			if( obj == null ) {
				retval = null;
			}
			else if( obj instanceof ICFBamTopProjectObj ) {
				retval = (ICFBamTopDomainObj) ((ICFBamTopProjectObj)obj).getRequiredContainerParentSDom();
			}
			else if( obj instanceof ICFBamLicenseObj ) {
				retval = (ICFBamTopDomainObj) ((ICFBamLicenseObj)obj).getRequiredContainerTopDomain();
			}
			else {
				retval = null;
			}
			return( retval );
		}

		public List<ICFIntTopProjectObj> getOptionalComponentsTopProject() {
			List<ICFIntTopProjectObj> retval;
			if( obj == null ) {
				retval = null;
			}
			else if( obj instanceof ICFBamTopDomainObj ) {
				retval = ((ICFBamTopDomainObj)obj).getOptionalComponentsTopProject();
			}
			else {
				retval = null;
			}
			return( retval );
		}

		public List<ICFIntTopProjectObj> getOptionalComponentsTopProject( boolean forceRead ) {
			List<ICFIntTopProjectObj> retval;
			if( obj == null ) {
				retval = null;
			}
			else if( obj instanceof ICFBamTopDomainObj ) {
				retval = ((ICFBamTopDomainObj)obj).getOptionalComponentsTopProject( forceRead );
			}
			else {
				retval = null;
			}
			return( retval );
		}

		public List<ICFIntSubProjectObj> getOptionalComponentsSubProject() {
			List<ICFIntSubProjectObj> retval;
			if( obj == null ) {
				retval = null;
			}
			else if( obj instanceof ICFBamTopProjectObj ) {
				retval = ((ICFBamTopProjectObj)obj).getOptionalComponentsSubProject();
			}
			else {
				retval = null;
			}
			return( retval );
		}

		public List<ICFIntSubProjectObj> getOptionalComponentsSubProject( boolean forceRead ) {
			List<ICFIntSubProjectObj> retval;
			if( obj == null ) {
				retval = null;
			}
			else if( obj instanceof ICFBamTopProjectObj ) {
				retval = ((ICFBamTopProjectObj)obj).getOptionalComponentsSubProject( forceRead );
			}
			else {
				retval = null;
			}
			return( retval );
		}

		public List<ICFIntMajorVersionObj> getOptionalComponentsMajorVer() {
			List<ICFIntMajorVersionObj> retval;
			if( obj == null ) {
				retval = null;
			}
			else if( obj instanceof ICFBamSubProjectObj ) {
				retval = ((ICFBamSubProjectObj)obj).getOptionalComponentsMajorVer();
			}
			else {
				retval = null;
			}
			return( retval );
		}

		public List<ICFIntMajorVersionObj> getOptionalComponentsMajorVer( boolean forceRead ) {
			List<ICFIntMajorVersionObj> retval;
			if( obj == null ) {
				retval = null;
			}
			else if( obj instanceof ICFBamSubProjectObj ) {
				retval = ((ICFBamSubProjectObj)obj).getOptionalComponentsMajorVer( forceRead );
			}
			else {
				retval = null;
			}
			return( retval );
		}

		public List<ICFIntMinorVersionObj> getOptionalComponentsMinorVer() {
			List<ICFIntMinorVersionObj> retval;
			if( obj == null ) {
				retval = null;
			}
			else if( obj instanceof ICFBamMajorVersionObj ) {
				retval = ((ICFBamMajorVersionObj)obj).getOptionalComponentsMinorVer();
			}
			else {
				retval = null;
			}
			return( retval );
		}

		public List<ICFIntMinorVersionObj> getOptionalComponentsMinorVer( boolean forceRead ) {
			List<ICFIntMinorVersionObj> retval;
			if( obj == null ) {
				retval = null;
			}
			else if( obj instanceof ICFBamMajorVersionObj ) {
				retval = ((ICFBamMajorVersionObj)obj).getOptionalComponentsMinorVer( forceRead );
			}
			else {
				retval = null;
			}
			return( retval );
		}

		public List<ICFBamSchemaDefObj> getOptionalComponentsSchemaDef() {
			List<ICFBamSchemaDefObj> retval;
			if( obj == null ) {
				retval = null;
			}
			else if( obj instanceof ICFBamMinorVersionObj ) {
				retval = ((ICFBamMinorVersionObj)obj).getOptionalComponentsSchemaDef();
			}
			else {
				retval = null;
			}
			return( retval );
		}

		public List<ICFBamSchemaDefObj> getOptionalComponentsSchemaDef( boolean forceRead ) {
			List<ICFBamSchemaDefObj> retval;
			if( obj == null ) {
				retval = null;
			}
			else if( obj instanceof ICFBamMinorVersionObj ) {
				retval = ((ICFBamMinorVersionObj)obj).getOptionalComponentsSchemaDef( forceRead );
			}
			else {
				retval = null;
			}
			return( retval );
		}

		public List<ICFLibAnyObj> getOptionalComponentsChildren() {
			List<ICFLibAnyObj> retval;
			if( obj == null ) {
				retval = new ArrayList<ICFLibAnyObj>();
				List<ICFIntTldObj> tldlist = ((ICFBamSchemaObj)customSchema).getTldTableObj().readTldByTenantIdx( customSchema.getTenantObj().getRequiredId() );
				if( tldlist != null ) {
					Iterator<ICFIntTldObj> tlditer = tldlist.iterator();
					while( tlditer.hasNext() ) {
						CFBamCustHierarchyNode node = new CFBamCustHierarchyNode();
						node.setObj( tlditer.next() );
						retval.add( node );
					}
				}
			}
			else if( obj instanceof ICFBamTopDomainObj ) {
				retval = new ArrayList<ICFLibAnyObj>();
				List<ICFIntTopProjectObj> topprojectlist = ((ICFBamTopDomainObj)obj).getOptionalComponentsTopProject();
				if( topprojectlist != null ) {
					Iterator<ICFIntTopProjectObj> topprojectiter = topprojectlist.iterator();
					while( topprojectiter.hasNext() ) {
						retval.add( topprojectiter.next() );
					}
				}
			}
			else if( obj instanceof ICFBamTopProjectObj ) {
				retval = new ArrayList<ICFLibAnyObj>();
				List<ICFIntSubProjectObj> subprojectlist = ((ICFBamTopProjectObj)obj).getOptionalComponentsSubProject();
				if( subprojectlist != null ) {
					Iterator<ICFIntSubProjectObj> subprojectiter = subprojectlist.iterator();
					while( subprojectiter.hasNext() ) {
						retval.add( subprojectiter.next() );
					}
				}
			}
			else if( obj instanceof ICFBamSubProjectObj ) {
				retval = new ArrayList<ICFLibAnyObj>();
				List<ICFIntMajorVersionObj> majorversionlist = ((ICFBamSubProjectObj)obj).getOptionalComponentsMajorVer();
				if( majorversionlist != null ) {
					Iterator<ICFIntMajorVersionObj> majorversioniter = majorversionlist.iterator();
					while( majorversioniter.hasNext() ) {
						retval.add( majorversioniter.next() );
					}
				}
			}
			else if( obj instanceof ICFBamMajorVersionObj ) {
				retval = new ArrayList<ICFLibAnyObj>();
				List<ICFIntMinorVersionObj> sublist = ((ICFBamMajorVersionObj)obj).getOptionalComponentsMinorVer();
				if( sublist != null ) {
					Iterator<ICFIntMinorVersionObj> subiter = sublist.iterator();
					while( subiter.hasNext() ) {
						retval.add( subiter.next() );
					}
				}
			}
			else if( obj instanceof ICFBamMinorVersionObj ) {
				retval = new ArrayList<ICFLibAnyObj>();
				List<ICFBamSchemaDefObj> sublist = ((ICFBamMinorVersionObj)obj).getOptionalComponentsSchemaDef();
				if( sublist != null ) {
					Iterator<ICFBamSchemaDefObj> subiter = sublist.iterator();
					while( subiter.hasNext() ) {
						retval.add( subiter.next() );
					}
				}
			}
			else {
				retval = null;
			}
			return( retval );
		}

		public List<ICFLibAnyObj> getOptionalComponentsChildren( boolean forceRead ) {
			List<ICFLibAnyObj> retval;
			if( obj == null ) {
				retval = new ArrayList<ICFLibAnyObj>();
				List<ICFIntTldObj> tldlist = ((ICFBamSchemaObj)customSchema).getTldTableObj().readTldByTenantIdx( customSchema.getTenantObj().getRequiredId(), forceRead );
				if( tldlist != null ) {
					Iterator<ICFIntTldObj> tlditer = tldlist.iterator();
					while( tlditer.hasNext() ) {
						CFBamCustHierarchyNode node = new CFBamCustHierarchyNode();
						node.setObj( tlditer.next() );
						retval.add( node );
					}
				}
			}
			else if( obj instanceof ICFBamTopDomainObj ) {
				retval = new ArrayList<ICFLibAnyObj>();
				List<ICFIntTopProjectObj> topprojectlist = ((ICFBamTopDomainObj)obj).getOptionalComponentsTopProject( forceRead );
				if( topprojectlist != null ) {
					Iterator<ICFIntTopProjectObj> topprojectiter = topprojectlist.iterator();
					while( topprojectiter.hasNext() ) {
						retval.add( topprojectiter.next() );
					}
				}
			}
			else if( obj instanceof ICFBamTopProjectObj ) {
				retval = new ArrayList<ICFLibAnyObj>();
				List<ICFIntSubProjectObj> subprojectlist = ((ICFBamTopProjectObj)obj).getOptionalComponentsSubProject( forceRead );
				if( subprojectlist != null ) {
					Iterator<ICFIntSubProjectObj> subprojectiter = subprojectlist.iterator();
					while( subprojectiter.hasNext() ) {
						retval.add( subprojectiter.next() );
					}
				}
			}
			else if( obj instanceof ICFBamSubProjectObj ) {
				retval = new ArrayList<ICFLibAnyObj>();
				List<ICFIntMajorVersionObj> majorversionlist = ((ICFBamSubProjectObj)obj).getOptionalComponentsMajorVer( forceRead );
				if( majorversionlist != null ) {
					Iterator<ICFIntMajorVersionObj> majorversioniter = majorversionlist.iterator();
					while( majorversioniter.hasNext() ) {
						retval.add( majorversioniter.next() );
					}
				}
			}
			else if( obj instanceof ICFBamMajorVersionObj ) {
				retval = new ArrayList<ICFLibAnyObj>();
				List<ICFIntMinorVersionObj> sublist = ((ICFBamMajorVersionObj)obj).getOptionalComponentsMinorVer( forceRead );
				if( sublist != null ) {
					Iterator<ICFIntMinorVersionObj> subiter = sublist.iterator();
					while( subiter.hasNext() ) {
						retval.add( subiter.next() );
					}
				}
			}
			else if( obj instanceof ICFBamMinorVersionObj ) {
				retval = new ArrayList<ICFLibAnyObj>();
				List<ICFBamSchemaDefObj> sublist = ((ICFBamMinorVersionObj)obj).getOptionalComponentsSchemaDef( forceRead );
				if( sublist != null ) {
					Iterator<ICFBamSchemaDefObj> subiter = sublist.iterator();
					while( subiter.hasNext() ) {
						retval.add( subiter.next() );
					}
				}
			}
			else {
				retval = null;
			}
			return( retval );
		}

		public String getOptionalDescription() {
			String retval;
			if( obj == null ) {
				retval = null;
			}
			else if( obj instanceof ICFBamTopDomainObj ) {
				retval = ((ICFBamTopDomainObj)obj).getOptionalDescription();
			}
			else if( obj instanceof ICFBamTopProjectObj ) {
				retval = ((ICFBamTopProjectObj)obj).getOptionalDescription();
			}
			else if( obj instanceof ICFBamSubProjectObj ) {
				retval = ((ICFBamSubProjectObj)obj).getOptionalDescription();
			}
			else if( obj instanceof ICFBamMajorVersionObj ) {
				retval = ((ICFBamMajorVersionObj)obj).getOptionalDescription();
			}
			else if( obj instanceof ICFBamMinorVersionObj ) {
				retval = ((ICFBamMinorVersionObj)obj).getOptionalDescription();
			}
			else if( obj instanceof ICFBamLicenseObj ) {
				retval = ((ICFBamLicenseObj)obj).getOptionalDescription();
			}
			else if( obj instanceof ICFBamSchemaDefObj ) {
				retval = ((ICFBamSchemaDefObj)obj).getOptionalDescription();
			}
			else {
				retval = null;
			}
			return( retval );
		}
	}

	protected CFBamCustHierarchyItem rootTreeItem = null;
	protected TreeTableView<CFBamCustHierarchyNode> treeTableView = null;

	protected CFHBox hboxMenu = null;
	protected CFButton buttonAdd = null;
	protected CFButton buttonAddTld = null;
	protected CFButton buttonAddTopDomain = null;
	protected CFButton buttonAddDomain = null;
	protected CFButton buttonAddTopProject = null;
	protected CFButton buttonAddSubProject = null;
	protected CFButton buttonAddMajorVersion = null;
	protected CFButton buttonAddMinorVersion = null;
	protected CFButton buttonAddLicense = null;
	protected CFButton buttonAddSchemaDef = null;
	protected CFButton buttonCancelAdd = null;
	protected CFButton buttonViewSelected = null;
	protected CFButton buttonEditSelected = null;
	protected CFButton buttonDeleteSelected = null;
	protected CFButton buttonSaveAs = null;
	protected CFButton buttonExitApp = null;
	protected CFButton buttonValidateModel = null;

	boolean addingObject = false;

	protected boolean isLoadedSchema( ICFBamSchemaDefObj schemaDef ) {
		if( definedVersion == null ) {
			return( true );
		}
		Iterator<ICFBamSchemaDefObj> iter = definedVersion.getOptionalComponentsSchemaDef().iterator();
		while( iter.hasNext() ) {
			if( iter.next() == schemaDef ) {
				return( true );
			}
		}
		return( false );
	}

	public CFBamCustEditorHierarchyPane(
		ICFFormManager formManager, 
		ICFBamCustEditorSchema argSchema,
		String argModelName )
	{
		super();
		final String S_ProcName = "construct";
		if( formManager == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				"formManager" );
		}
		if( argModelName == null ) {
			modelName = "";
		}
		else {
			modelName = argModelName;
		}
		cfFormManager = formManager;
		customSchema = argSchema;
		setMinWidth( 640.0 );
		setMinHeight( 320.0 );

		if( cfEngine  == null ) {

			MSSBamCFPrefs prefs = new MSSBamCFPrefs();
			if( ! prefs.loadPreferences( log, false ) ) {
				CFConsole.message( "Could not load MSS Code Factory preferences file.  You must have a valid MSS Code Factory installation to use the editor" );
				prefs = null;
			}
			userPrefs = prefs;

			String prefsGenDir = userPrefs.getRootGenDir();
			if( ( prefsGenDir == null ) || ( prefsGenDir.length() <= 0 ) ) {
				throw new RuntimeException( S_ProcName + "Preferences RootGenDir is null or empty" );
			}

			int idxLast = prefsGenDir.length() - 1;
			String rootGenDir =
				(	( prefsGenDir.lastIndexOf( '/' ) == idxLast )
					|| ( prefsGenDir.lastIndexOf( '\\' ) == idxLast )
					|| ( prefsGenDir.lastIndexOf( File.separatorChar ) == idxLast ) )
				? prefsGenDir
				: prefsGenDir + File.separator;

			//		Initialize the generation engine

			ICFGenKbSchema genKbSchema = new CFGenKbRamSchema();
			ICFGenKbSchemaObj genKbBLSchema = new CFGenKbSchemaObj();
			genKbBLSchema.setBackingStore( genKbSchema );

			ICFGenKbClusterObj origCluster = genKbBLSchema.getClusterTableObj().newInstance();
			ICFGenKbClusterEditObj editCluster = origCluster.beginEdit();
			editCluster.setRequiredFullDomName( "msscf.sourceforge.net" );
			origCluster = editCluster.create();
			editCluster = null;

			ICFGenKbTenantObj origTenant = genKbBLSchema.getTenantTableObj().newInstance();
			ICFGenKbTenantEditObj editTenant = origTenant.beginEdit();
			editTenant.setRequiredContainerCluster( origCluster );
			editTenant.setRequiredTenantName( "internal" );
			origTenant = editTenant.create();
			editTenant = null;

			ICFBamSchemaObj bamBLSchema = (ICFBamSchemaObj)argSchema.getSchema();

			try {
				cfEngine = new MSSBamCFEngine();
				cfEngine.setLog( log );
				((MSSBamCFEngine)cfEngine).init( "1", genKbBLSchema, origTenant, bamBLSchema, rootGenDir );
				//		Configure parser

				log.message( "Initializing rule cartridge parser..." );

				Iterator<String> cartridgePath = userPrefs.getCartridgePathIterator();
				while( cartridgePath.hasNext() ) {
					String cartridgeDir = (String)cartridgePath.next();
					MssCFRuleCartridgeParser.addCartridgePath( cartridgeDir );
				}

				Iterator<String> modelPath = userPrefs.getModelPathIterator();
				while( modelPath.hasNext() ) {
					String modelDir = (String)modelPath.next();
					CFBamXmlLoader.addModelPath( modelDir );
				}

		//		Load the cartridge

				MssCFRuleCartridgeParser cartridgeParser = new MssCFRuleCartridgeParser( cfEngine, log );

				String[] parsedToolSetNames = null;
				try {
					// cartridgeParser.loadRuleCartridge( "net-sourceforge-msscf-27-layered+msscf" );
				}
				catch( Exception e ) {
					log.message( "Could not load rule cartridge: " + e.getMessage() );
		            throw( e );
				}
				catch( Error e ) {
					log.message( "Could not load rule cartridge: " + e.getMessage() );
		            throw( e );
				}
				parsedToolSetNames = MssCFRuleCartridgeParser.getToolSetNames();

				if( ( parsedToolSetNames != null )
				 && ( parsedToolSetNames.length > 0 ) )
				{
					StringBuffer msg = new StringBuffer();
					msg.append( "Rule cartridge specified tool set names " );
					for( int idxName = 0; idxName < parsedToolSetNames.length; idxName ++ )
					{
						if( idxName > 0 )
						{
							msg.append( ", " );
						}
						msg.append( parsedToolSetNames[idxName] );
					}
					log.message( msg.toString() );
				}
				else {
					//log.message( "Rule cartridge did not define tool set names to process." );
				}

				// Instantiate Bam Model parser

				CFBamXmlLoader bamParser = new CFBamXmlLoader( (MSSBamCFEngine)cfEngine, log );
				bamParser.setSchemaObj( bamBLSchema );
				bamParser.setTenant( (ICFBamTenantObj)bamBLSchema.getSecTenant() );

				// Load model

				if( modelName.length() > 0 ) {
					bamParser.loadTenant( modelName );
					definedVersion = (ICFBamMinorVersionObj)(bamParser.getDefinedProjectVersion());
				}
			}
			catch( Throwable t ) {
				cfEngine = null;
				CFConsole.formException( S_FormName, S_ProcName, t );
			}
		}

		labelTitle = new CFLabel();
		labelTitle.setText( "Bam Hierarchy" );
		Font f = labelTitle.getFont();
		Font largeBold = Font.font( f.getFamily(), FontWeight.BOLD, 20 );
		labelTitle.setFont( largeBold );
		labelTitle.setMinHeight( 35 );
		labelTitle.setMaxHeight( 35 );
		labelTitle.setMinWidth( 200 );
		labelTitle.setAlignment( Pos.CENTER );

		rootTreeItem = buildRootItem();

		TreeTableColumn<CFBamCustHierarchyNode,String> columnName = new TreeTableColumn<>( "Name" );
		columnName.setPrefWidth( 250 );
		columnName.setCellValueFactory((CellDataFeatures<CFBamCustHierarchyNode,String> p ) ->
			new ReadOnlyStringWrapper(
				( ( p.getValue().getValue() == null )
					? "."
					: p.getValue().getValue().getObjName() ) ) );

		TreeTableColumn<CFBamCustHierarchyNode,String> columnClass = new TreeTableColumn<>( "Class" );
		columnClass.setPrefWidth( 200 );
		columnClass.setCellValueFactory((CellDataFeatures<CFBamCustHierarchyNode,String> p ) ->
			new ReadOnlyStringWrapper(
				( ( p.getValue().getValue() == null )
					? "."
					: p.getValue().getValue().getGenDefName() ) ) );

		TreeTableColumn<CFBamCustHierarchyNode,String> columnDescription = new TreeTableColumn<>( "Description" );
		columnDescription.setPrefWidth( 300 );
		columnDescription.setCellValueFactory((CellDataFeatures<CFBamCustHierarchyNode,String> p ) ->
			new ReadOnlyStringWrapper(
				( ( p.getValue().getValue() == null )
					? "."
					: p.getValue().getValue().getOptionalDescription() ) ) );

		treeTableView = new TreeTableView<>();
		treeTableView.setRoot( rootTreeItem );
		treeTableView.getColumns().add( columnName );
		treeTableView.getColumns().add( columnClass );
		treeTableView.getColumns().add( columnDescription );
		treeTableView.setPrefWidth( 500 );
		treeTableView.setMinWidth( 300 );
		treeTableView.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<TreeItem<CFBamCustHierarchyNode>>() {
					@Override public void changed( ObservableValue<? extends TreeItem<CFBamCustHierarchyNode>> observable,
						TreeItem<CFBamCustHierarchyNode> oldValue,
						TreeItem<CFBamCustHierarchyNode> newValue )
					{
						if( newValue != null ) {
							setJavaFXFocus( newValue.getValue() );
						}
						else {
							setJavaFXFocus( null );
						}
					}
				});

		borderSub = new CFBorderPane();
		borderSub.setTop( getPanelHBoxMenu() );
		borderSub.setCenter( treeTableView );

		setTop( labelTitle );
		setCenter( borderSub );
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

	public void forceCancelAndClose() {
		if( cfFormManager != null ) {
			if( cfFormManager.getCurrentForm() == this ) {
				cfFormManager.closeCurrentForm();
			}
		}
	}

	public ICFBamCustEditorSchema getCustomSchema() {
		return( customSchema );
	}

	public void setCustomSchema( ICFBamCustEditorSchema argSchema ) {
		final String S_ProcName = "setCustomSchema";
		final String S_ArgNameSchema = "argSchema";
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				S_ArgNameSchema );
		}
		customSchema = argSchema;
	}

	public CFBamCustHierarchyItem buildRootItem() {

		CFBamCustHierarchyItem rootItem = new CFBamCustHierarchyItem();
		List<ICFIntTldObj> tldlist = ((ICFBamSchemaObj)customSchema.getSchema()).getTldTableObj().readTldByTenantIdx( customSchema.getSchema().getAuthorization().getSecTenantId(), true );
		if( tldlist == null ) {
			return( rootItem );
		}

		ObservableList<TreeItem<CFBamCustHierarchyNode>> children = rootItem.getChildren();
		ICFBamTldObj tldobj;
		CFBamCustHierarchyNode node;
		TreeItem<CFBamCustHierarchyNode> treeItem;
		List<ICFIntTopDomainObj> topdomainlist;
		Iterator<ICFIntTopDomainObj> topdomainiter;
		ICFBamTopDomainObj topdomainobj;
		ObservableList<TreeItem<CFBamCustHierarchyNode>> topdomainchildren;
		CFBamCustHierarchyNode topdomainnode;
		List<ICFIntLicenseObj> licenselist;
		Iterator<ICFIntLicenseObj> licenseiter;
		ICFBamLicenseObj licenseobj;
		ObservableList<TreeItem<CFBamCustHierarchyNode>> licensechildren;
		CFBamCustHierarchyNode licensenode;
		List<ICFBamSchemaDefObj> schemadeflist;
		Iterator<ICFBamSchemaDefObj> schemadefiter;
		ICFBamSchemaDefObj schemadefobj;
		ObservableList<TreeItem<CFBamCustHierarchyNode>> schemadefchildren;
		CFBamCustHierarchyNode schemadefnode;
		List<ICFIntTopProjectObj> topprojectlist;
		Iterator<ICFIntTopProjectObj> topprojectiter;
		ICFBamTopProjectObj topprojectobj;
		ObservableList<TreeItem<CFBamCustHierarchyNode>> topprojectchildren;
		CFBamCustHierarchyNode topprojectnode;
		List<ICFIntSubProjectObj> subprojectlist;
		Iterator<ICFIntSubProjectObj> subprojectiter;
		ICFBamSubProjectObj subprojectobj;
		ObservableList<TreeItem<CFBamCustHierarchyNode>> subprojectchildren;
		CFBamCustHierarchyNode subprojectnode;
		List<ICFIntMajorVersionObj> majorversionlist;
		Iterator<ICFIntMajorVersionObj> majorversioniter;
		ICFBamMajorVersionObj majorversionobj;
		ObservableList<TreeItem<CFBamCustHierarchyNode>> majorversionchildren;
		CFBamCustHierarchyNode majorversionnode;
		List<ICFIntMinorVersionObj> minorversionlist;
		Iterator<ICFIntMinorVersionObj> minorversioniter;
		ICFBamMinorVersionObj minorversionobj;
		ObservableList<TreeItem<CFBamCustHierarchyNode>> minorversionchildren;
		CFBamCustHierarchyNode minorversionnode;
		ObservableList<TreeItem<CFBamCustHierarchyNode>> tldchildren;

		Iterator<ICFIntTldObj> tlditer = tldlist.iterator();
		while( tlditer.hasNext() ) {
			tldobj = (ICFBamTldObj) tlditer.next();
			if( tldobj != null ) {
				node = new CFBamCustHierarchyNode();
				node.setObj( tldobj );
				treeItem = new TreeItem<CFBamCustHierarchyNode>();
				treeItem.setValue( node );
				treeItem.setExpanded( false );
				children.add( treeItem );
				tldchildren = treeItem.getChildren();
				topdomainlist = tldobj.getOptionalComponentsTopDomain();
				if( topdomainlist != null ) {
					topdomainiter = topdomainlist.iterator();
					if( topdomainiter != null ) {
						while( topdomainiter.hasNext() ) {
							topdomainobj = (ICFBamTopDomainObj) topdomainiter.next();
							if( topdomainobj != null ) {
								topdomainnode = new CFBamCustHierarchyNode();
								topdomainnode.setObj( topdomainobj );
								treeItem = new TreeItem<CFBamCustHierarchyNode>();
								treeItem.setValue( topdomainnode );
								treeItem.setExpanded( false );
								tldchildren.add( treeItem );
								topdomainchildren = treeItem.getChildren();
								licenselist = topdomainobj.getOptionalComponentsLicense();
								if( licenselist != null ) {
									licenseiter = licenselist.iterator();
									if( licenseiter != null ) {
										while( licenseiter.hasNext() ) {
											licenseobj = (ICFBamLicenseObj) licenseiter.next();
											if( licenseobj != null ) {
												licensenode = new CFBamCustHierarchyNode();
												licensenode.setObj( licenseobj );
												treeItem = new TreeItem<CFBamCustHierarchyNode>();
												treeItem.setValue( licensenode );
												treeItem.setExpanded( false );
												topdomainchildren.add( treeItem );
												licensechildren = treeItem.getChildren();
											}
										}
									}
								}
								topprojectlist = topdomainobj.getOptionalComponentsTopProject();
								if( topprojectlist != null ) {
									topprojectiter = topprojectlist.iterator();
									if( topprojectiter != null ) {
										while( topprojectiter.hasNext() ) {
											topprojectobj = (ICFBamTopProjectObj) topprojectiter.next();
											if( topprojectobj != null ) {
												topprojectnode = new CFBamCustHierarchyNode();
												topprojectnode.setObj( topprojectobj );
												treeItem = new TreeItem<CFBamCustHierarchyNode>();
												treeItem.setValue( topprojectnode );
												treeItem.setExpanded( false );
												topdomainchildren.add( treeItem );
												topprojectchildren = treeItem.getChildren();
												subprojectlist = topprojectobj.getOptionalComponentsSubProject();
												if( subprojectlist != null ) {
													subprojectiter = subprojectlist.iterator();
													if( subprojectiter != null ) {
														while( subprojectiter.hasNext() ) {
															subprojectobj = (ICFBamSubProjectObj) subprojectiter.next();
															if( subprojectobj != null ) {
																subprojectnode = new CFBamCustHierarchyNode();
																subprojectnode.setObj( subprojectobj );
																treeItem = new TreeItem<CFBamCustHierarchyNode>();
																treeItem.setValue( subprojectnode );
																treeItem.setExpanded( false );
																topprojectchildren.add( treeItem );
																subprojectchildren = treeItem.getChildren();
																majorversionlist = subprojectobj.getOptionalComponentsMajorVer();
																if( majorversionlist != null ) {
																	majorversioniter = majorversionlist.iterator();
																	if( majorversioniter != null ) {
																		while( majorversioniter.hasNext() ) {
																			majorversionobj = (ICFBamMajorVersionObj) majorversioniter.next();
																			if( majorversionobj != null ) {
																				majorversionnode = new CFBamCustHierarchyNode();
																				majorversionnode.setObj( majorversionobj );
																				treeItem = new TreeItem<CFBamCustHierarchyNode>();
																				treeItem.setValue( majorversionnode );
																				treeItem.setExpanded( false );
																				subprojectchildren.add( treeItem );
																				majorversionchildren = treeItem.getChildren();
																				minorversionlist = majorversionobj.getOptionalComponentsMinorVer();
																				if( minorversionlist != null ) {
																					minorversioniter = minorversionlist.iterator();
																					if( minorversioniter != null ) {
																						while( minorversioniter.hasNext() ) {
																							minorversionobj = (ICFBamMinorVersionObj) minorversioniter.next();
																							if( minorversionobj != null ) {
																								minorversionnode = new CFBamCustHierarchyNode();
																								minorversionnode.setObj( minorversionobj );
																								treeItem = new TreeItem<CFBamCustHierarchyNode>();
																								treeItem.setValue( minorversionnode );
																								treeItem.setExpanded( false );
																								majorversionchildren.add( treeItem );
																								minorversionchildren = treeItem.getChildren();
																								schemadeflist = minorversionobj.getOptionalComponentsSchemaDef();
																								if( schemadeflist != null ) {
																									schemadefiter = schemadeflist.iterator();
																									if( schemadefiter != null ) {
																										while( schemadefiter.hasNext() ) {
																											schemadefobj = (ICFBamSchemaDefObj) schemadefiter.next();
																											if( schemadefobj != null ) {
																												schemadefnode = new CFBamCustHierarchyNode();
																												schemadefnode.setObj( schemadefobj );
																												treeItem = new TreeItem<CFBamCustHierarchyNode>();
																												treeItem.setValue( schemadefnode );
																												treeItem.setExpanded( false );
																												minorversionchildren.add( treeItem );
																												schemadefchildren = treeItem.getChildren();
																											}
																										}
																									}
																								}
																							}
																						}
																					}
																				}
																			}
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		rootItem.setExpanded( true );
		return( rootItem );
	}

	public void setJavaFXFocus( ICFLibAnyObj value ) {
		final String S_ProcName = "setJavaFXFocus";
		if( ( value == null ) || ( value instanceof CFBamCustHierarchyNode ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"CFBamCustHierarchyNode" );
		}
		adjustListButtons();
	}

	public CFBamCustHierarchyNode getJavaFXFocusAsCustomHierarchyNode() {
		return( (CFBamCustHierarchyNode)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsCustomHierarchyNode( CFBamCustHierarchyNode value ) {
		setJavaFXFocus( value );
	}

	class ViewEditClosedCallback implements ICFFormClosedCallback {
		public ViewEditClosedCallback() {
		}

		@Override
		public void formClosed( ICFLibAnyObj affectedObject ) {
			if( affectedObject != null ) {
				if( addingObject ) {
					TreeItem<CFBamCustHierarchyNode> selectedItem;
					if( affectedObject instanceof ICFBamTldObj ) {
						selectedItem = treeTableView.getRoot();
					}
					else {
						selectedItem = treeTableView.getSelectionModel().getSelectedItem();
						if( selectedItem == null ) {
							selectedItem = treeTableView.getRoot();
						}
					}

					CFBamCustHierarchyNode newNode = new CFBamCustHierarchyNode();
					newNode.setObj( affectedObject );
					TreeItem<CFBamCustHierarchyNode> treeItem = new TreeItem<CFBamCustHierarchyNode>();
					treeItem.setValue( newNode );
					treeItem.setExpanded( false );
					selectedItem.getChildren().add( treeItem );
					selectedItem.setExpanded( true );

					addingObject = false;
				}
				// Hack from stackoverflow to fix JavaFX TableView refresh issue
				((TreeTableColumn)treeTableView.getColumns().get(1)).setVisible( false );
				((TreeTableColumn)treeTableView.getColumns().get(1)).setVisible( true );
			}
			adjustListButtons();
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
				TreeItem<CFBamCustHierarchyNode> selectedItem = treeTableView.getSelectionModel().getSelectedItem();
				if( selectedItem != null ) {
					CFBamCustHierarchyNode node = selectedItem.getValue();
					if( node.getObj() == deletedObject ) {
						TreeItem<CFBamCustHierarchyNode> parentItem = selectedItem.getParent();
						if( parentItem != null ) {
							ObservableList<TreeItem<CFBamCustHierarchyNode>> children = parentItem.getChildren();
							int len = children.size();
							TreeItem<CFBamCustHierarchyNode> cur;
							CFBamCustHierarchyNode curNode;
							for( int idx = 0; idx < len; idx ++ ) {
								cur = children.get( idx );
								if( cur != null ) {
									curNode = cur.getValue();
									if( curNode != null ) {
										if( deletedObject == curNode.getObj() ) {
											children.remove( idx );
											break;
										}
									}
								}
							}
							treeTableView.getSelectionModel().clearSelection();
							setJavaFXFocus( null );
							if( len == 1 ) {
								parentItem.setExpanded( false );
							}
							// Hack from stackoverflow to fix JavaFX TableView refresh issue
							((TreeTableColumn)treeTableView.getColumns().get(1)).setVisible( false );
							((TreeTableColumn)treeTableView.getColumns().get(1)).setVisible( true );
						}
					}
				}
			}
			adjustListButtons();
		}

		@Override
		public void formClosed( ICFLibAnyObj affectedObject ) {
		}
	}

	protected DeleteCallback deleteCallback = null;

	public ICFDeleteCallback getDeleteCallback() {
		if( deleteCallback == null ) {
			deleteCallback = new DeleteCallback();
		}
		return( deleteCallback );
	}

	public CFHBox getPanelHBoxMenu() {
		if( hboxMenu == null ) {
			hboxMenu = new CFHBox( 10 );

				buttonAddTld = new CFButton();
				buttonAddTld.setText( "Add TLD" );
				buttonAddTld.setOnAction( new EventHandler<ActionEvent>() {
					@Override public void handle( ActionEvent e ) {
						final String S_ProcName = "handle";
						try {
							ICFBamJavaFXSchema javafxSchema = (ICFBamJavaFXSchema)customSchema.getJavaFXSchema();
							ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
							ICFBamTldObj obj = (ICFBamTldObj)schemaObj.getTldTableObj().newInstance();
							CFBorderPane frame = javafxSchema.getTldFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), false );
							ICFBamTldEditObj edit = (ICFBamTldEditObj)( obj.beginEdit() );
							if( edit == null ) {
								throw new CFLibNullArgumentException( getClass(),
									S_ProcName,
									0,
									"edit" );
							}
							ICFSecTenantObj secTenant = schemaObj.getSecTenant();
							edit.setRequiredContainerTenant( secTenant );

							ICFIntJavaFXTldPaneCommon jpanelCommon = (ICFIntJavaFXTldPaneCommon)frame;
							jpanelCommon.setJavaFXFocus( obj );
							jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
							cfFormManager.pushForm( frame );
							borderSub.setLeft( null );
						}
						catch( Throwable t ) {
							CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
						}
					}
				} );
				buttonAddTld.setMinWidth( 200 );
				buttonAddTld.setMaxWidth( 200 );

				buttonAddTopDomain = new CFButton();
				buttonAddTopDomain.setText( "Add Top Domain" );
				buttonAddTopDomain.setOnAction( new EventHandler<ActionEvent>() {
					@Override public void handle( ActionEvent e ) {
						final String S_ProcName = "handle";
						try {
							ICFBamJavaFXSchema javafxSchema = (ICFBamJavaFXSchema)customSchema.getJavaFXSchema();
							ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
							ICFBamTopDomainObj obj = (ICFBamTopDomainObj)schemaObj.getTopDomainTableObj().newInstance();
							CFBorderPane frame = javafxSchema.getTopDomainFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), false );
							ICFBamTopDomainEditObj edit = (ICFBamTopDomainEditObj)( obj.beginEdit() );
							if( edit == null ) {
								throw new CFLibNullArgumentException( getClass(),
									S_ProcName,
									0,
									"edit" );
							}
							ICFSecTenantObj secTenant = schemaObj.getSecTenant();
							edit.setRequiredOwnerTenant( secTenant );
							ICFBamTldObj container = (ICFBamTldObj)getJavaFXFocusAsCustomHierarchyNode().getObj();
							if( container == null ) {
								throw new CFLibNullArgumentException( getClass(),
									S_ProcName,
									0,
									"JavaFXContainer" );
							}
							edit.setRequiredContainerParentTld( container );
							ICFIntJavaFXTopDomainPaneCommon jpanelCommon = (ICFIntJavaFXTopDomainPaneCommon)frame;
							jpanelCommon.setJavaFXFocus( obj );
							jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
							cfFormManager.pushForm( frame );
							borderSub.setLeft( null );
						}
						catch( Throwable t ) {
							CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
						}
					}
				});
				buttonAddTopDomain.setMinWidth( 200 );
				buttonAddTopDomain.setMaxWidth( 200 );

				buttonAddTopProject = new CFButton();
				buttonAddTopProject.setText( "Add Top Project" );
				buttonAddTopProject.setOnAction( new EventHandler<ActionEvent>() {
					@Override public void handle( ActionEvent e ) {
						final String S_ProcName = "handle";
						try {
							ICFBamJavaFXSchema javafxSchema = (ICFBamJavaFXSchema)customSchema.getJavaFXSchema();
							ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
							ICFBamTopProjectObj obj = (ICFBamTopProjectObj)schemaObj.getTopProjectTableObj().newInstance();
							CFBorderPane frame = javafxSchema.getTopProjectFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), false );
							ICFBamTopProjectEditObj edit = (ICFBamTopProjectEditObj)( obj.beginEdit() );
							if( edit == null ) {
								throw new CFLibNullArgumentException( getClass(),
									S_ProcName,
									0,
									"edit" );
							}
							ICFSecTenantObj secTenant = schemaObj.getSecTenant();
							edit.setRequiredOwnerTenant( secTenant );
							ICFLibAnyObj container = getJavaFXFocusAsCustomHierarchyNode().getObj();
							if( container == null ) {
								throw new CFLibNullArgumentException( getClass(),
									S_ProcName,
									0,
									"JavaFXContainer" );
							}
							if( container instanceof ICFBamTopDomainObj ) {
								edit.setRequiredContainerParentSDom( (ICFBamTopDomainObj)container );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"container",
									container,
									"ICBamTopDomainObj" );
							}
							ICFIntJavaFXTopProjectPaneCommon jpanelCommon = (ICFIntJavaFXTopProjectPaneCommon)frame;
							jpanelCommon.setJavaFXFocus( obj );
							jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
							cfFormManager.pushForm( frame );
							borderSub.setLeft( null );
						}
						catch( Throwable t ) {
							CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
						}
					}
				});
				buttonAddTopProject.setMinWidth( 200 );
				buttonAddTopProject.setMaxWidth( 200 );

				buttonAddSubProject = new CFButton();
				buttonAddSubProject.setText( "Add Sub-Project" );
				buttonAddSubProject.setOnAction( new EventHandler<ActionEvent>() {
					@Override public void handle( ActionEvent e ) {
						final String S_ProcName = "handle";
						try {
							ICFBamJavaFXSchema javafxSchema = (ICFBamJavaFXSchema)customSchema.getJavaFXSchema();
							ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
							ICFBamSubProjectObj obj = (ICFBamSubProjectObj)schemaObj.getSubProjectTableObj().newInstance();
							CFBorderPane frame = javafxSchema.getSubProjectFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), false );
							ICFBamSubProjectEditObj edit = (ICFBamSubProjectEditObj)( obj.beginEdit() );
							if( edit == null ) {
								throw new CFLibNullArgumentException( getClass(),
									S_ProcName,
									0,
									"edit" );
							}
							ICFSecTenantObj secTenant = schemaObj.getSecTenant();
							edit.setRequiredOwnerTenant( secTenant );
							ICFBamTopProjectObj container = (ICFBamTopProjectObj)getJavaFXFocusAsCustomHierarchyNode().getObj();
							if( container == null ) {
								throw new CFLibNullArgumentException( getClass(),
									S_ProcName,
									0,
									"JavaFXContainer" );
							}
							edit.setRequiredContainerParentTPrj( container );
							ICFIntJavaFXSubProjectPaneCommon jpanelCommon = (ICFIntJavaFXSubProjectPaneCommon)frame;
							jpanelCommon.setJavaFXFocus( obj );
							jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
							cfFormManager.pushForm( frame );
							borderSub.setLeft( null );
						}
						catch( Throwable t ) {
							CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
						}
					}
				});
				buttonAddSubProject.setMinWidth( 200 );
				buttonAddSubProject.setMaxWidth( 200 );

				buttonAddMajorVersion = new CFButton();
				buttonAddMajorVersion.setText( "Add Major Version" );
				buttonAddMajorVersion.setOnAction( new EventHandler<ActionEvent>() {
					@Override public void handle( ActionEvent e ) {
						final String S_ProcName = "handle";
						try {
							ICFBamJavaFXSchema javafxSchema = (ICFBamJavaFXSchema)customSchema.getJavaFXSchema();
							ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
							ICFBamMajorVersionObj obj = (ICFBamMajorVersionObj)schemaObj.getMajorVersionTableObj().newInstance();
							CFBorderPane frame = javafxSchema.getMajorVersionFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), false );
							ICFBamMajorVersionEditObj edit = (ICFBamMajorVersionEditObj)( obj.beginEdit() );
							if( edit == null ) {
								throw new CFLibNullArgumentException( getClass(),
									S_ProcName,
									0,
									"edit" );
							}
							ICFSecTenantObj secTenant = schemaObj.getSecTenant();
							edit.setRequiredOwnerTenant( secTenant );
							ICFBamSubProjectObj container = (ICFBamSubProjectObj)getJavaFXFocusAsCustomHierarchyNode().getObj();
							if( container == null ) {
								throw new CFLibNullArgumentException( getClass(),
									S_ProcName,
									0,
									"JavaFXContainer" );
							}
							edit.setRequiredContainerParentSPrj( container );
							ICFIntJavaFXMajorVersionPaneCommon jpanelCommon = (ICFIntJavaFXMajorVersionPaneCommon)frame;
							jpanelCommon.setJavaFXFocus( obj );
							jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
							cfFormManager.pushForm( frame );
							borderSub.setLeft( null );
						}
						catch( Throwable t ) {
							CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
						}
					}
				});
				buttonAddMajorVersion.setMinWidth( 200 );
				buttonAddMajorVersion.setMaxWidth( 200 );

				buttonAddMinorVersion = new CFButton();
				buttonAddMinorVersion.setText( "Add Minor Version" );
				buttonAddMinorVersion.setOnAction( new EventHandler<ActionEvent>() {
					@Override public void handle( ActionEvent e ) {
						final String S_ProcName = "handle";
						try {
							ICFBamJavaFXSchema javafxSchema = (ICFBamJavaFXSchema)customSchema.getJavaFXSchema();
							ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
							ICFBamMinorVersionObj obj = (ICFBamMinorVersionObj)schemaObj.getMinorVersionTableObj().newInstance();
							CFBorderPane frame = javafxSchema.getMinorVersionFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), false );
							ICFBamMinorVersionEditObj edit = (ICFBamMinorVersionEditObj)( obj.beginEdit() );
							if( edit == null ) {
								throw new CFLibNullArgumentException( getClass(),
									S_ProcName,
									0,
									"edit" );
							}
							ICFSecTenantObj secTenant = schemaObj.getSecTenant();
							edit.setRequiredOwnerTenant( secTenant );
							ICFBamMajorVersionObj container = (ICFBamMajorVersionObj)getJavaFXFocusAsCustomHierarchyNode().getObj();
							if( container == null ) {
								throw new CFLibNullArgumentException( getClass(),
									S_ProcName,
									0,
									"JavaFXContainer" );
							}
							edit.setRequiredContainerParentMajVer( container );
							ICFIntJavaFXMinorVersionPaneCommon jpanelCommon = (ICFIntJavaFXMinorVersionPaneCommon)frame;
							jpanelCommon.setJavaFXFocus( obj );
							jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
							cfFormManager.pushForm( frame );
							borderSub.setLeft( null );
						}
						catch( Throwable t ) {
							CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
						}
					}
				});
				buttonAddMinorVersion.setMinWidth( 200 );
				buttonAddMinorVersion.setMaxWidth( 200 );

				buttonAddLicense = new CFButton();
				buttonAddLicense.setText( "Add License" );
				buttonAddLicense.setOnAction( new EventHandler<ActionEvent>() {
					@Override public void handle( ActionEvent e ) {
						final String S_ProcName = "handle";
						try {
							ICFBamJavaFXSchema javafxSchema = (ICFBamJavaFXSchema)customSchema.getJavaFXSchema();
							ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
							ICFBamLicenseObj obj = (ICFBamLicenseObj)schemaObj.getLicenseTableObj().newInstance();
							CFBorderPane frame = javafxSchema.getLicenseFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), false );
							ICFBamLicenseEditObj edit = (ICFBamLicenseEditObj)( obj.beginEdit() );
							if( edit == null ) {
								throw new CFLibNullArgumentException( getClass(),
									S_ProcName,
									0,
									"edit" );
							}
							ICFSecTenantObj secTenant = schemaObj.getSecTenant();
							edit.setRequiredOwnerTenant( secTenant );
							ICFBamTopDomainObj container = (ICFBamTopDomainObj)getJavaFXFocusAsCustomHierarchyNode().getObj();
							if( container == null ) {
								throw new CFLibNullArgumentException( getClass(),
									S_ProcName,
									0,
									"JavaFXContainer" );
							}
							edit.setRequiredContainerTopDomain( container );
							ICFIntJavaFXLicensePaneCommon jpanelCommon = (ICFIntJavaFXLicensePaneCommon)frame;
							jpanelCommon.setJavaFXFocus( obj );
							jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
							cfFormManager.pushForm( frame );
							borderSub.setLeft( null );
						}
						catch( Throwable t ) {
							CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
						}
					}
				});
				buttonAddLicense.setMinWidth( 200 );
				buttonAddLicense.setMaxWidth( 200 );

				buttonAddSchemaDef = new CFButton();
				buttonAddSchemaDef.setText( "Add SchemaDef" );
				buttonAddSchemaDef.setOnAction( new EventHandler<ActionEvent>() {
					@Override public void handle( ActionEvent e ) {
						final String S_ProcName = "handle";
						try {
							ICFBamJavaFXSchema javafxSchema = (ICFBamJavaFXSchema)customSchema.getJavaFXSchema();
							ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
							ICFBamSchemaDefObj obj = (ICFBamSchemaDefObj)schemaObj.getSchemaDefTableObj().newInstance();
							CFBorderPane frame = javafxSchema.getSchemaDefFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
							ICFBamSchemaDefEditObj edit = (ICFBamSchemaDefEditObj)( obj.beginEdit() );
							if( edit == null ) {
								throw new CFLibNullArgumentException( getClass(),
									S_ProcName,
									0,
									"edit" );
							}
							ICFSecTenantObj secTenant = schemaObj.getSecTenant();
							edit.setRequiredOwnerTenant( secTenant );
							ICFBamMinorVersionObj container = (ICFBamMinorVersionObj)getJavaFXFocusAsCustomHierarchyNode().getObj();
							if( container == null ) {
								throw new CFLibNullArgumentException( getClass(),
									S_ProcName,
									0,
									"JavaFXContainer" );
							}
							edit.setRequiredContainerMinorVersion( container );
							ICFBamJavaFXSchemaDefPaneCommon jpanelCommon = (ICFBamJavaFXSchemaDefPaneCommon)frame;
							jpanelCommon.setJavaFXFocus( obj );
							jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
							cfFormManager.pushForm( frame );
							borderSub.setLeft( null );
						}
						catch( Throwable t ) {
							CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
						}
					}
				});
				buttonAddSchemaDef.setMinWidth( 200 );
				buttonAddSchemaDef.setMaxWidth( 200 );

				buttonCancelAdd = new CFButton();
				buttonCancelAdd.setText( "Cancel Add..." );
				buttonCancelAdd.setOnAction( new EventHandler<ActionEvent>() {
					@Override public void handle( ActionEvent e ) {
						final String S_ProcName = "handle";
						try {
							borderSub.setLeft( null );
							addingObject = false;
						}
						catch( Throwable t ) {
							CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
						}
					}
				});

			buttonAdd = new CFButton();
			buttonAdd.setText( "Add..." );
			buttonAdd.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						ScrollPane scrollMenuAdd = null;
						CFVBox vboxMenuAdd = new CFVBox( 10 );
						vboxMenuAdd.getChildren().add( buttonAddTld );
						CFBamCustHierarchyNode node = getJavaFXFocusAsCustomHierarchyNode();
						if( node != null ) {
							ICFLibAnyObj container = node.getObj();
							if( container != null ) {
								if( container instanceof ICFBamTldObj ) {
									vboxMenuAdd.getChildren().add( buttonAddTopDomain );
								}
								else if( container instanceof ICFBamTopDomainObj ) {
									vboxMenuAdd.getChildren().add( buttonAddTopProject );
									vboxMenuAdd.getChildren().add( buttonAddLicense );
								}
								else if( container instanceof ICFBamTopProjectObj ) {
									vboxMenuAdd.getChildren().add( buttonAddSubProject );
								}
								else if( container instanceof ICFBamSubProjectObj ) {
									vboxMenuAdd.getChildren().add( buttonAddMajorVersion );
								}
								else if( container instanceof ICFBamMajorVersionObj ) {
									vboxMenuAdd.getChildren().add( buttonAddMinorVersion );
								}
								else if( container instanceof ICFBamMinorVersionObj ) {
									vboxMenuAdd.getChildren().add( buttonAddSchemaDef );
								}
								else if( container instanceof ICFBamLicenseObj ) {
								}
								else if( container instanceof ICFBamSchemaDefObj ) {
								}
								else {
									throw new CFLibUnsupportedClassException( getClass(),
										S_ProcName,
										"container",
										container,
										"ICFBamTldObj, ICFBamTopDomainObj, ICFBamDomainObj, ICFBamTopProjectObj, ICFBamSubProjectObj, ICFBamMajorVersionObj, ICFBamMinorVersionObj, ICFBamSchemaDefObj" );
								}
							}
						}
						vboxMenuAdd.getChildren().add( buttonCancelAdd );

						scrollMenuAdd = new ScrollPane();
						scrollMenuAdd.setMinWidth( 240 );
						scrollMenuAdd.setMaxWidth( 240 );
						scrollMenuAdd.setFitToWidth( true );
						scrollMenuAdd.setHbarPolicy( ScrollBarPolicy.NEVER );
						scrollMenuAdd.setVbarPolicy( ScrollBarPolicy.AS_NEEDED );
						scrollMenuAdd.setContent( vboxMenuAdd );

						borderSub.setLeft( scrollMenuAdd );
						addingObject = true;
						adjustListButtons();
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonAdd );

			buttonViewSelected = new CFButton();
			buttonViewSelected.setText( "View Selected" );
			buttonViewSelected.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						addingObject = false;
						ICFBamJavaFXSchema javafxSchema = (ICFBamJavaFXSchema)customSchema.getJavaFXSchema();
						ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
						if( schemaObj == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"schemaObj" );
						}
						CFBamCustHierarchyNode node = getJavaFXFocusAsCustomHierarchyNode();
						if( node != null ) {
							ICFLibAnyObj selectedInstance = node.getObj();
							if( selectedInstance != null ) {
								if( selectedInstance instanceof ICFBamTldObj ) {
									ICFBamTldObj obj = (ICFBamTldObj)selectedInstance;
									CFBorderPane frame = javafxSchema.getTldFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), false );
									((ICFIntJavaFXTldPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
									cfFormManager.pushForm( frame );
								}
								else if( selectedInstance instanceof ICFBamTopDomainObj ) {
									ICFBamTopDomainObj obj = (ICFBamTopDomainObj)selectedInstance;
									CFBorderPane frame = javafxSchema.getTopDomainFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), false );
									((ICFIntJavaFXTopDomainPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
									cfFormManager.pushForm( frame );
								}
								else if( selectedInstance instanceof ICFBamTopProjectObj ) {
									ICFBamTopProjectObj obj = (ICFBamTopProjectObj)selectedInstance;
									CFBorderPane frame = javafxSchema.getTopProjectFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), false );
									((ICFIntJavaFXTopProjectPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
									cfFormManager.pushForm( frame );
								}
								else if( selectedInstance instanceof ICFBamSubProjectObj ) {
									ICFBamSubProjectObj obj = (ICFBamSubProjectObj)selectedInstance;
									CFBorderPane frame = javafxSchema.getSubProjectFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), false );
									((ICFIntJavaFXSubProjectPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
									cfFormManager.pushForm( frame );
								}
								else if( selectedInstance instanceof ICFBamMajorVersionObj ) {
									ICFBamMajorVersionObj obj = (ICFBamMajorVersionObj)selectedInstance;
									CFBorderPane frame = javafxSchema.getMajorVersionFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), false );
									((ICFIntJavaFXMajorVersionPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
									cfFormManager.pushForm( frame );
								}
								else if( selectedInstance instanceof ICFBamMinorVersionObj ) {
									ICFBamMinorVersionObj obj = (ICFBamMinorVersionObj)selectedInstance;
									CFBorderPane frame = javafxSchema.getMinorVersionFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), false );
									((ICFIntJavaFXMinorVersionPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
									cfFormManager.pushForm( frame );
								}
								else if( selectedInstance instanceof ICFBamLicenseObj ) {
									ICFBamLicenseObj obj = (ICFBamLicenseObj)selectedInstance;
									CFBorderPane frame = javafxSchema.getLicenseFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), false );
									((ICFIntJavaFXLicensePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
									cfFormManager.pushForm( frame );
								}
								else if( selectedInstance instanceof ICFBamSchemaDefObj ) {
									ICFBamSchemaDefObj obj = (ICFBamSchemaDefObj)selectedInstance;
									CFBorderPane frame = javafxSchema.getSchemaDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
									((ICFBamJavaFXSchemaDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
									cfFormManager.pushForm( frame );
								}
								else {
									throw new CFLibUnsupportedClassException( getClass(),
										S_ProcName,
										"selectedInstance",
										selectedInstance,
										"ICFBamTldObj, ICFBamTopDomainObj, ICFBamDomainObj, ICFBamTopProjectObj, ICFBamSubProjectObj, ICFBamMajorVersionObj, ICFBamMinorVersionObj, ICFBamLicenseObj, ICFBamSchemaDefObj" );
								}
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
			buttonEditSelected.setText( "Edit Selected" );
			buttonEditSelected.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						addingObject = false;
						ICFBamJavaFXSchema javafxSchema = (ICFBamJavaFXSchema)customSchema.getJavaFXSchema();
						ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
						if( schemaObj == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"schemaObj" );
						}
						CFBamCustHierarchyNode node = getJavaFXFocusAsCustomHierarchyNode();
						if( node != null ) {
							ICFLibAnyObj selectedInstance = node.getObj();
							if( selectedInstance != null ) {
								if( selectedInstance instanceof ICFBamTldObj ) {
									ICFBamTldObj obj = (ICFBamTldObj)selectedInstance;
									CFBorderPane frame = javafxSchema.getTldFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), false );
									((ICFIntJavaFXTldPaneCommon)frame).setPaneMode( CFPane.PaneMode.Edit );
									cfFormManager.pushForm( frame );
								}
								else if( selectedInstance instanceof ICFBamTopDomainObj ) {
									ICFBamTopDomainObj obj = (ICFBamTopDomainObj)selectedInstance;
									CFBorderPane frame = javafxSchema.getTopDomainFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), false );
									((ICFIntJavaFXTopDomainPaneCommon)frame).setPaneMode( CFPane.PaneMode.Edit );
									cfFormManager.pushForm( frame );
								}
								else if( selectedInstance instanceof ICFBamTopProjectObj ) {
									ICFBamTopProjectObj obj = (ICFBamTopProjectObj)selectedInstance;
									CFBorderPane frame = javafxSchema.getTopProjectFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), false );
									((ICFIntJavaFXTopProjectPaneCommon)frame).setPaneMode( CFPane.PaneMode.Edit );
									cfFormManager.pushForm( frame );
								}
								else if( selectedInstance instanceof ICFBamSubProjectObj ) {
									ICFBamSubProjectObj obj = (ICFBamSubProjectObj)selectedInstance;
									CFBorderPane frame = javafxSchema.getSubProjectFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), false );
									((ICFIntJavaFXSubProjectPaneCommon)frame).setPaneMode( CFPane.PaneMode.Edit );
									cfFormManager.pushForm( frame );
								}
								else if( selectedInstance instanceof ICFBamMajorVersionObj ) {
									ICFBamMajorVersionObj obj = (ICFBamMajorVersionObj)selectedInstance;
									CFBorderPane frame = javafxSchema.getMajorVersionFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), false );
									((ICFIntJavaFXMajorVersionPaneCommon)frame).setPaneMode( CFPane.PaneMode.Edit );
									cfFormManager.pushForm( frame );
								}
								else if( selectedInstance instanceof ICFBamMinorVersionObj ) {
									ICFBamMinorVersionObj obj = (ICFBamMinorVersionObj)selectedInstance;
									CFBorderPane frame = javafxSchema.getMinorVersionFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), false );
									((ICFIntJavaFXMinorVersionPaneCommon)frame).setPaneMode( CFPane.PaneMode.Edit );
									cfFormManager.pushForm( frame );
								}
								else if( selectedInstance instanceof ICFBamLicenseObj ) {
									ICFBamLicenseObj obj = (ICFBamLicenseObj)selectedInstance;
									CFBorderPane frame = javafxSchema.getLicenseFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), false );
									((ICFIntJavaFXLicensePaneCommon)frame).setPaneMode( CFPane.PaneMode.Edit );
									cfFormManager.pushForm( frame );
								}
								else if( selectedInstance instanceof ICFBamSchemaDefObj ) {
									ICFBamSchemaDefObj obj = (ICFBamSchemaDefObj)selectedInstance;
									CFBorderPane frame = javafxSchema.getSchemaDefFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
									((ICFBamJavaFXSchemaDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.Edit );
									cfFormManager.pushForm( frame );
								}
								else {
									throw new CFLibUnsupportedClassException( getClass(),
										S_ProcName,
										"selectedInstance",
										selectedInstance,
										"ICFBamTldObj, ICFBamTopDomainObj, ICFBamDomainObj, ICFBamTopProjectObj, ICFBamSubProjectObj, ICFBamMajorVersionObj, ICFBamMinorVersionObj, ICFBamLicenseObj, ICFBamSchemaDefObj" );
								}
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonEditSelected );

			buttonDeleteSelected = new CFButton();
			buttonDeleteSelected.setText( "Delete Selected" );
			buttonDeleteSelected.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						addingObject = false;
						ICFBamJavaFXSchema javafxSchema = (ICFBamJavaFXSchema)customSchema.getJavaFXSchema();
						ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
						if( schemaObj == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"schemaObj" );
						}
						CFBamCustHierarchyNode node = getJavaFXFocusAsCustomHierarchyNode();
						if( node != null ) {
							ICFLibAnyObj selectedInstance = node.getObj();
							if( selectedInstance != null ) {
								if( selectedInstance instanceof ICFBamTldObj ) {
									ICFBamTldObj obj = (ICFBamTldObj)selectedInstance;
									CFBorderPane frame = javafxSchema.getTldFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
									((ICFIntJavaFXTldPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
									cfFormManager.pushForm( frame );
								}
								else if( selectedInstance instanceof ICFBamTopDomainObj ) {
									ICFBamTopDomainObj obj = (ICFBamTopDomainObj)selectedInstance;
									CFBorderPane frame = javafxSchema.getTopDomainFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
									((ICFIntJavaFXTopDomainPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
									cfFormManager.pushForm( frame );
								}
								else if( selectedInstance instanceof ICFBamTopProjectObj ) {
									ICFBamTopProjectObj obj = (ICFBamTopProjectObj)selectedInstance;
									CFBorderPane frame = javafxSchema.getTopProjectFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
									((ICFIntJavaFXTopProjectPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
									cfFormManager.pushForm( frame );
								}
								else if( selectedInstance instanceof ICFBamSubProjectObj ) {
									ICFBamSubProjectObj obj = (ICFBamSubProjectObj)selectedInstance;
									CFBorderPane frame = javafxSchema.getSubProjectFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
									((ICFIntJavaFXSubProjectPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
									cfFormManager.pushForm( frame );
								}
								else if( selectedInstance instanceof ICFBamMajorVersionObj ) {
									ICFBamMajorVersionObj obj = (ICFBamMajorVersionObj)selectedInstance;
									CFBorderPane frame = javafxSchema.getMajorVersionFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
									((ICFIntJavaFXMajorVersionPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
									cfFormManager.pushForm( frame );
								}
								else if( selectedInstance instanceof ICFBamMinorVersionObj ) {
									ICFBamMinorVersionObj obj = (ICFBamMinorVersionObj)selectedInstance;
									CFBorderPane frame = javafxSchema.getMinorVersionFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
									((ICFIntJavaFXMinorVersionPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
									cfFormManager.pushForm( frame );
								}
								else if( selectedInstance instanceof ICFBamLicenseObj ) {
									ICFBamLicenseObj obj = (ICFBamLicenseObj)selectedInstance;
									CFBorderPane frame = javafxSchema.getLicenseFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
									((ICFIntJavaFXLicensePaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
									cfFormManager.pushForm( frame );
								}
								else if( selectedInstance instanceof ICFBamSchemaDefObj ) {
									ICFBamSchemaDefObj obj = (ICFBamSchemaDefObj)selectedInstance;
									CFBorderPane frame = javafxSchema.getSchemaDefFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
									((ICFBamJavaFXSchemaDefPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
									cfFormManager.pushForm( frame );
								}
								else {
									throw new CFLibUnsupportedClassException( getClass(),
										S_ProcName,
										"selectedInstance",
										selectedInstance,
										"ICFBamTldObj, ICFBamTopDomainObj, ICFBamDomainObj, ICFBamTopProjectObj, ICFBamSubProjectObj, ICFBamMajorVersionObj, ICFBamMinorVersionObj, ICFBamLicenseObj, ICFBamSchemaDefObj" );
								}
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonDeleteSelected );

			buttonValidateModel = new CFButton();
			buttonValidateModel.setText( "Validate Model" );
			buttonValidateModel.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						addingObject = false;
						ICFBamJavaFXSchema javafxSchema = (ICFBamJavaFXSchema)customSchema.getJavaFXSchema();
						ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
						if( schemaObj == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"schemaObj" );
						}
						CFBamCustHierarchyNode node = getJavaFXFocusAsCustomHierarchyNode();
						if( node != null ) {
							ICFLibAnyObj selectedInstance = node.getObj();
							if( selectedInstance != null ) {
								if( selectedInstance instanceof ICFBamSchemaDefObj ) {
									ICFBamSchemaDefObj schemaDef = (ICFBamSchemaDefObj)selectedInstance;
									CFBamCustEditorValidateSchemaForm validationForm = new CFBamCustEditorValidateSchemaForm( cfFormManager,
										javafxSchema,
										schemaDef );
									cfFormManager.pushForm( validationForm );
								}
								else {
									throw new CFLibUnsupportedClassException( getClass(),
										S_ProcName,
										"selectedInstance",
										selectedInstance,
										"ICFBamSchemaDefObj" );
								}
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonValidateModel );

			buttonSaveAs = new CFButton();
			buttonSaveAs.setText( "Save As" );
			buttonSaveAs.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						addingObject = false;
						ICFBamJavaFXSchema javafxSchema = (ICFBamJavaFXSchema)customSchema.getJavaFXSchema();
						ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
						if( schemaObj == null ) {
							throw new CFLibNullArgumentException( getClass(),
								S_ProcName,
								0,
								"schemaObj" );
						}
						CFBamCustHierarchyNode node = getJavaFXFocusAsCustomHierarchyNode();
						if( node != null ) {
							ICFLibAnyObj selectedInstance = node.getObj();
							if( selectedInstance != null ) {
								if( selectedInstance instanceof ICFBamSchemaDefObj ) {

									ICFBamSchemaDefObj schemaDef = (ICFBamSchemaDefObj)selectedInstance;

									String defaultName = schemaDef.getObjName() + ".xml";
									ICFLibAnyObj chaseObj = schemaDef.getObjScope();
									while( ( chaseObj != null ) && ( ! ( chaseObj instanceof ICFBamTldObj ) ) ) {
										defaultName = chaseObj.getObjName() + "-" + defaultName;
										chaseObj = chaseObj.getObjScope();
									}
									if( chaseObj != null ) {
										defaultName = chaseObj.getObjName() + "-" + defaultName;
									}

									if( fileChooser == null ) {
										fileChooser = new FileChooser();
									}
									MSSBamCFPrefs userPrefs = getCFPrefs();
									Iterator<String> modelPaths = userPrefs.getModelPathIterator();
									if( ! modelPaths.hasNext() ) {
										throw new CFLibUsageException( getClass(),
											S_ProcName,
											"User preferences does not specify at least one model path entry; the MSS Code Factory installation is not configured correctly." );
									}
									String firstPath = modelPaths.next();
									File pathFile = new File( firstPath );
									if( ! ( pathFile.exists() && pathFile.isDirectory() && pathFile.canRead() )) {
										throw new CFLibUsageException( getClass(),
											S_ProcName,
											"First model path entry \"" + firstPath + "\" does not exist, is not a directory, or is not readable" );
									}
									fileChooser.setInitialDirectory( pathFile );
									fileChooser.setInitialFileName( defaultName );

									Window window = getScene().getWindow();

									File fileChosen = fileChooser.showSaveDialog( window );
									if( fileChosen == null ) {
										return;
									}

									String fileName = fileChosen.getAbsolutePath();

									if( ( fileName == null ) || ( fileName.length() <= 0 ) ) {
										CFConsole.message( "File name must be specified" );
										return;
									}

									if( fileChosen.exists() ) {
										if( ! fileChosen.isFile() ) {
											CFConsole.message( "Not a file: \"" + fileName + "\"" );
											return;
										}
										if( ! fileChosen.canWrite() ) {
											CFConsole.message( "Write permission denied for file \"" + fileName + "\"" );
											return;
										}
									}

									CFConsole.message( "Preparing export data... (Not implemented yet)" );

									MSSBamCFModelValidator validator = new MSSBamCFModelValidator();
									validator.propagateNameChanges( schemaDef );

									CFBamCustEditorFormatModel fmt = new CFBamCustEditorFormatModel();
									String strData = fmt.formatModel( schemaDef );

									CFConsole.message( "Saving file \"" + fileName + "\"..." );

									try {
										FileOutputStream output = new FileOutputStream( fileChosen );
										output.write( strData.getBytes( StandardCharsets.UTF_8) );
										output.close();
									}
									catch( IOException x ) {
										CFConsole.message( "Error saving to file \"" + fileName + "\"" );
										return;
									}

									CFConsole.message( "File saved" );
								}
								else {
									throw new CFLibUnsupportedClassException( getClass(),
										S_ProcName,
										"selectedInstance",
										selectedInstance,
										"ICFBamSchemaDefObj" );
								}
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonSaveAs );

			buttonExitApp = new CFButton();
			buttonExitApp.setVisible( true );
			buttonExitApp.setMinWidth( 200 );
			buttonExitApp.setMaxWidth( 200 );
			buttonExitApp.setPrefWidth( 200 );
			buttonExitApp.setMinHeight( 25 );
			buttonExitApp.setMaxHeight( 25 );
			buttonExitApp.setPrefHeight( 25 );
			hboxMenu.getChildren().add( buttonExitApp );
			buttonExitApp.setText( "Exit Application" );
			buttonExitApp.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					try {
						Platform.exit();
						System.exit( 0 );
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
		}

		return( hboxMenu );
	}

	public void adjustListButtons() {
		boolean enableState;
		boolean allowAdds;
		CFBamCustHierarchyNode selectedObj = getJavaFXFocusAsCustomHierarchyNode();
		if( selectedObj == null ) {
			enableState = false;
		}
		else {
			enableState = ! addingObject;
		}

		if( borderSub.getLeft() != null ) {
			allowAdds = false;
		}
		else {
			allowAdds = true;
		}

		if( cfEngine == null ) {
			if( buttonAdd != null ) {
				buttonAdd.setDisable( true );
			}
			if( buttonViewSelected != null ) {
				buttonViewSelected.setDisable( true );
			}
			if( buttonEditSelected != null ) {
				buttonEditSelected.setDisable( true );
			}
			if( buttonDeleteSelected != null ) {
				buttonDeleteSelected.setDisable( true );
			}
			if( buttonValidateModel != null ) {
				buttonValidateModel.setDisable( true );
			}
			if( buttonSaveAs != null ) {
				buttonSaveAs.setDisable( true );
			}
			if( buttonExitApp != null ) {
				buttonExitApp.setDisable( false );
			}
		}
		else {
			if( buttonAdd != null ) {
				buttonAdd.setDisable( ! allowAdds );
			}
			if( buttonViewSelected != null ) {
				buttonViewSelected.setDisable( ! enableState );
			}
			if( buttonEditSelected != null ) {
				buttonEditSelected.setDisable( ! enableState );
			}
			if( buttonDeleteSelected != null ) {
				buttonDeleteSelected.setDisable( ! enableState );
			}
			if( buttonExitApp != null ) {
				buttonExitApp.setDisable( false );
			}
			if( buttonValidateModel != null ) {
				if( selectedObj == null ) {
					buttonValidateModel.setDisable( true );
				}
				else {
					ICFLibAnyObj obj = selectedObj.getObj();
					if( obj instanceof ICFBamSchemaDefObj ) {
						buttonValidateModel.setDisable( false );
					}
					else {
						buttonValidateModel.setDisable( true );
					}
				}
			}
			if( buttonSaveAs != null ) {
				if( selectedObj == null ) {
					buttonSaveAs.setDisable( true );
				}
				else {
					ICFLibAnyObj obj = selectedObj.getObj();
					if( obj instanceof ICFBamSchemaDefObj ) {
						buttonSaveAs.setDisable( false );
					}
					else {
						buttonSaveAs.setDisable( true );
					}
				}
			}
		}
	}
}
