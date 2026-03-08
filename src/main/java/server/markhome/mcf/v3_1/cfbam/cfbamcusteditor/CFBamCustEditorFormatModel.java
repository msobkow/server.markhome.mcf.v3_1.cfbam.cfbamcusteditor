// Description: Format a model for export

/*
 *	server.markhome.mcf.CFBam
 *
 *	Copyright (c) 2016-2026 Mark Stephen Sobkow
 *	
 *	Mark's Code Fractal 3.1 CFBam - Business Application Model
 *	
 *	This file is part of Mark's Code Fractal CFBam.
 *	
 *	Mark's Code Fractal CFBam is available under dual commercial license from
 *	Mark Stephen Sobkow, or under the terms of the GNU General Public License,
 *	Version 3 or later.
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
 *	
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;

/**
 *	The ICFBamSwingSchema defines the interface for the shared schema
 *	object that is bound to a client.  The various implementations of
 *	facets cast the shared CFBamSchemaObj as required to access their
 *	data.
 *	<p>
 *	The formats of the configuration files don't change between applications,
 *	so only the CFBam-specified implementation is used.  It has all the
 *	attributes needed to set up client-server database connections (not that I
 *	code to that style any more.)
 */
public class CFBamCustEditorFormatModel
{
	protected int indentLevel = 0;
	protected StringBuffer buff = new StringBuffer();
	protected List<ICFBamTableObj> alphabeticalTableList = null;
	protected List<ICFBamValueObj> alphabeticalTypeList = null;
	protected HashMap<CFBamValuePKey,ICFBamValueObj> typeMap = null;
	protected HashMap<CFBamScopePKey,ICFBamTableObj> tableMap = null;

	public CFBamCustEditorFormatModel() {
	}

	public String formatModel( ICFBamSchemaDefObj schemaDef ) {
		final String S_ProcName = "formatModel";
		if( schemaDef == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				"schemaDef" );
		}
		indentLevel = 0;
		buff = new StringBuffer();

		List<ICFBamTableObj> tableDefs = schemaDef.getOptionalComponentsTables();
		int numTables = tableDefs.size();
		ICFBamTableObj[] arrayOfTable = new ICFBamTableObj[numTables];
		Iterator<ICFBamTableObj> iter = tableDefs.iterator();
		int idx = 0;
		while( iter.hasNext() && ( idx < numTables ) ) {
			arrayOfTable[idx++] = iter.next();
		}
		Arrays.sort( arrayOfTable, new TableNameComparator() );
		alphabeticalTableList = new LinkedList<ICFBamTableObj>();
		for( idx = 0; idx < numTables; idx++ ) {
			alphabeticalTableList.add( arrayOfTable[idx] );
		}

		List<ICFBamValueObj> typeDefs = schemaDef.getOptionalComponentsTypes();
		int numTypes = typeDefs.size();
		ICFBamValueObj[] arrayOfType = new ICFBamValueObj[numTypes];
		Iterator<ICFBamValueObj> iterType = typeDefs.iterator();
		idx = 0;
		while( iterType.hasNext() && ( idx < numTypes ) ) {
			arrayOfType[idx++] = iterType.next();
		}
		Arrays.sort( arrayOfType, new ValueNameComparator() );
		alphabeticalTypeList = new LinkedList<ICFBamValueObj>();
		for( idx = 0; idx < numTypes; idx++ ) {
			alphabeticalTypeList.add( arrayOfType[idx] );
		}

		tableMap = new HashMap<CFBamScopePKey,ICFBamTableObj>();
		ICFBamTableObj table;
		Iterator<ICFBamTableObj> tableIter = alphabeticalTableList.iterator();
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			if( null != table.getOptionalLookupDefSchema() ) {
				tableMap.put( table.getPKey(), table );
			}
		}

		typeMap = new HashMap<CFBamValuePKey,ICFBamValueObj>();
		ICFBamValueObj type;
		iterType = alphabeticalTypeList.iterator();
		while( iterType.hasNext() ) {
			type = iterType.next();
			if( null != type.getOptionalLookupDefSchema() ) {
				typeMap.put( type.getPKey(), type );
			}
		}

		formatXmlHeader();
		formatLicenseHeader( schemaDef );
		formatMSSBamStart( schemaDef );
		indent();
		formatLicense( schemaDef );
		formatSchemaDef( schemaDef );
		dedent();
		formatMSSBamEnd();

		return( buff.toString() );
	}

	protected void indent() {
		indentLevel ++;
	}

	protected void dedent() {
		if( indentLevel > 0 ) {
			indentLevel --;
		}
	}

	protected void emitIndent() {
		for( int i = 0; i < indentLevel; i++ ) {
			buff.append( '\t' );
		}
	}

	class TableNameComparator
	implements Comparator<ICFBamTableObj>
	{
		public TableNameComparator() {
		}

		@Override public int compare(ICFBamTableObj o1, ICFBamTableObj o2) {
			if( o1 == null ) {
				if( o2 == null ) {
					return( 0 );
				}
				else {
					return( -1 );
				}
			}
			else {
				if( o2 == null ) {
					return( 1 );
				}
				else {
					String name1 = o1.getObjName();
					String name2 = o2.getObjName();
					return( name1.compareTo( name2 ) );
				}
			}
		}
	}

	class ValueNameComparator
	implements Comparator<ICFBamValueObj>
	{
		public ValueNameComparator() {
		}

		@Override public int compare(ICFBamValueObj o1, ICFBamValueObj o2) {
			if( o1 == null ) {
				if( o2 == null ) {
					return( 0 );
				}
				else {
					return( -1 );
				}
			}
			else {
				if( o2 == null ) {
					return( 1 );
				}
				else {
					String name1 = o1.getObjName();
					String name2 = o2.getObjName();
					return( name1.compareTo( name2 ) );
				}
			}
		}
	}

	protected void formatXmlHeader() {
		buff.append( "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" );
	}

	protected void formatLicenseHeader( ICFBamSchemaDefObj schemaDef ) {
		ICFIntLicenseObj license = schemaDef.getOptionalLookupDefaultLicense();
		buff.append( "<!--\n" );
		String descr = schemaDef.getOptionalDescription();
		if( ( descr == null ) || ( descr.length() <= 0 ) ) {
			descr = schemaDef.getObjName();
		}
		buff.append( " *\t" + descr + "\n" );
		buff.append( " *\n" );
		buff.append( " *\tCopyright (c) "
			+ schemaDef.getRequiredCopyrightPeriod()
			+ " "
			+ schemaDef.getRequiredCopyrightHolder()
			+ "\n" );
		if( license != null ) {
			String licenseText = license.getOptionalEmbeddedText();
			if( ( licenseText != null ) && ( licenseText.length() > 0 ) ) {
				if( ! licenseText.startsWith( "\n" ) ) {
					buff.append( " *\n" );
				}
				String indented = " *\t" + licenseText.replaceAll( "\n", "\n *\t" );
				if( licenseText.endsWith( "\n" ) ) {
					buff.append( indented.substring( 0, indented.length() - 1 ) );
				}
				else {
					buff.append( indented );
					buff.append( "\n *" );
				}
			}
		}
		buff.append( "-->\n" );
	}

	protected void formatMSSBamStart( ICFBamSchemaDefObj schemaDef ) {
		String schemaName = schemaDef.getObjName();
		String descr = schemaDef.getOptionalDescription();
		if( ( descr == null ) || ( descr.length() <= 0 ) ) {
			descr = schemaName;
		}
		String revision = "";
		String projectRoot = "";
		ICFLibAnyObj obj = schemaDef.getObjScope();
		if( obj != null ) {
			if( obj instanceof ICFIntMinorVersionObj ) {
				ICFLibAnyObj majorVersion = obj.getObjScope();
				revision = majorVersion.getObjName() + "." + obj.getObjName();
			}
			else if( obj instanceof ICFIntMajorVersionObj ) {
				revision = obj.getObjName();
			}
			else {
				revision = "1.0";
			}
			projectRoot = obj.getObjName();
			obj = obj.getObjScope();
			while( ( obj != null ) && ( ! ( obj instanceof ICFBamTldObj ) ) ) {
				projectRoot = obj.getObjName() + "." + projectRoot;
				obj = obj.getObjScope();
			}
			if( obj != null ) {
				projectRoot = obj.getObjName() + "." + projectRoot;
			}
		}
		buff.append( "<MSSBam\n" );
		buff.append( "\t\txmlns=\"uri://msobkow/2.0.12/mssbam\"\n" );
		buff.append( "\t\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" );
		buff.append( "\t\txmlns:xs=\"http://www.w3.org/2001/XMLSchema\"\n" );
		buff.append( "\t\txsi:schemaLocation=\"http://msscf.org:8088/msscf/2.0.12/mssbam.xsd http://msscf.org:8088/msscf/2.0.12/mssbam.xsd\"\n" );
		buff.append( "\t\tName=\"" + CFLibXmlUtil.formatXmlString( schemaName ) + "\"\n" );
		buff.append( "\t\tRevision=\"" + CFLibXmlUtil.formatXmlString( revision ) + "\"\n" );
		buff.append( "\t\tDescription=\"" + CFLibXmlUtil.formatXmlString( descr ) + "\"\n" );
		buff.append( "\t\tProjectRoot=\"" + CFLibXmlUtil.formatXmlString( projectRoot ) + "\" >\n" );
	}

	protected void formatMSSBamEnd() {
		buff.append( "</MSSBam>\n" );
	}

	protected void emitBeginNode( ICFLibAnyObj obj ) {
		if( obj instanceof ICFIntTldObj ) {
			emitIndent();
			buff.append( "<Tld Name=\"" + CFLibXmlUtil.formatXmlString( obj.getObjName() ) + "\" >\n" );
			indent();
		}
		else if( obj instanceof ICFIntTopDomainObj ) {
			emitIndent();
			buff.append( "<TopDomain Name=\"" + CFLibXmlUtil.formatXmlString( obj.getObjName() ) + "\" >\n" );
			indent();
		}
		else if( obj instanceof ICFIntTopProjectObj ) {
			emitIndent();
			buff.append( "<TopProject Name=\"" + CFLibXmlUtil.formatXmlString( obj.getObjName() ) + "\" >\n" );
			indent();
		}
		else if( obj instanceof ICFIntSubProjectObj ) {
			emitIndent();
			buff.append( "<SubProject Name=\"" + CFLibXmlUtil.formatXmlString( obj.getObjName() ) + "\" >\n" );
			indent();
		}
		else if( obj instanceof ICFIntMajorVersionObj ) {
			emitIndent();
			buff.append( "<MajorVersion Name=\"" + CFLibXmlUtil.formatXmlString( obj.getObjName() ) + "\" >\n" );
			indent();
		}
		else if( obj instanceof ICFIntMinorVersionObj ) {
			emitIndent();
			buff.append( "<MinorVersion Name=\"" + CFLibXmlUtil.formatXmlString( obj.getObjName() ) + "\" >\n" );
			indent();
		}
	}

	protected void emitEndNode( ICFLibAnyObj obj ) {
		if( obj instanceof ICFIntTldObj ) {
			dedent();
			emitIndent();
			buff.append( "</Tld>\n" );
		}
		else if( obj instanceof ICFIntTopDomainObj ) {
			dedent();
			emitIndent();
			buff.append( "</TopDomain>\n" );
		}
		else if( obj instanceof ICFIntTopProjectObj ) {
			dedent();
			emitIndent();
			buff.append( "</TopProject>\n" );
		}
		else if( obj instanceof ICFIntSubProjectObj ) {
			dedent();
			emitIndent();
			buff.append( "</SubProject>\n" );
		}
		else if( obj instanceof ICFIntMajorVersionObj ) {
			dedent();
			emitIndent();
			buff.append( "</MajorVersion>\n" );
		}
		else if( obj instanceof ICFIntMinorVersionObj ) {
			dedent();
			emitIndent();
			buff.append( "</MinorVersion>\n" );
		}
	}

	protected void emitBeginHierarchy( ICFLibAnyObj obj ) {
		ICFLibAnyObj scope = obj.getObjScope();
		if( scope != null ) {
			emitBeginHierarchy( scope );
		}
		emitBeginNode( obj );
	}

	protected void emitEndHierarchy( ICFLibAnyObj obj ) {
		ICFLibAnyObj scope = obj.getObjScope();
		emitEndNode( obj );
		if( scope != null ) {
			emitEndHierarchy( scope );
		}
	}

	protected void formatLicense( ICFBamSchemaDefObj schemaDef ) {
		ICFIntLicenseObj license = schemaDef.getOptionalLookupDefaultLicense();
		if( license == null ) {
			return;
		}
		ICFLibAnyObj scope = license.getObjScope();
		emitBeginHierarchy( scope );

		String name = license.getObjName();
		String descr = license.getOptionalDescription();
		if( descr == null ) {
			descr = "";
		}
		String embeddedText = license.getOptionalEmbeddedText();
		if( embeddedText == null ) {
			embeddedText = "";
		}
		emitIndent();
		buff.append( "<License Name=\"" + CFLibXmlUtil.formatXmlString( name ) + "\"\n" );
		emitIndent();
		buff.append("\t\tDescription=\"" + CFLibXmlUtil.formatXmlString( descr ) + "\">" );
		buff.append( CFLibXmlUtil.formatXmlStringWhitespacePreserve( embeddedText ) );
		buff.append( "</License>\n" );

		emitEndHierarchy( scope );
	}

	protected void formatSchemaDef( ICFBamSchemaDefObj schemaDef ) {
		final String S_ProcName = "formatSchemaDef";
		ICFLibAnyObj scope = schemaDef.getObjScope();
		emitBeginHierarchy( scope );

		int saveIndent = indentLevel;
		indentLevel = 0;

		String name = schemaDef.getObjName();
		String descr = schemaDef.getOptionalDescription();
		if( descr == null ) {
			descr = "";
		}
		String dbName = schemaDef.getOptionalDbName();
		String shortName = schemaDef.getOptionalShortName();
		String label = schemaDef.getOptionalLabel();
		String shortDescription = schemaDef.getOptionalShortDescription();
		ICFIntLicenseObj license = schemaDef.getOptionalLookupDefaultLicense();
		String licenseName = null;
		if( license != null ) {
			licenseName = license.getObjFullName();
		}
		String copyrightHolder = schemaDef.getRequiredCopyrightHolder();
		String copyrightPeriod = schemaDef.getRequiredCopyrightPeriod();
		String projectURL = schemaDef.getRequiredProjectURL();
		String authorEMail = schemaDef.getRequiredAuthorEMail();
		String publishURI = schemaDef.getRequiredPublishURI();
		emitIndent();
		buff.append( "<SchemaDef Name=\"" + CFLibXmlUtil.formatXmlString( name ) + "\"" );
		if( ( dbName != null ) && ( dbName.length() > 0 ) ) {
			buff.append( "\n");
			emitIndent();
			buff.append( "\t\tDbName=\"" + CFLibXmlUtil.formatXmlString( dbName ) + "\"" );
		}
		if( ( shortName != null ) && ( shortName.length() > 0 ) ) {
			buff.append( "\n");
			emitIndent();
			buff.append( "\t\tShortName=\"" + CFLibXmlUtil.formatXmlString( shortName ) + "\"" );
		}
		emitIndent();
		buff.append("\n\t\tDescription=\"" + CFLibXmlUtil.formatXmlString( descr ) + "\"" );
		if( ( shortDescription != null ) && ( shortDescription.length() > 0 ) ) {
			buff.append( "\n");
			emitIndent();
			buff.append( "\t\tShortDescription=\"" + CFLibXmlUtil.formatXmlString( shortDescription ) + "\"" );
		}
		if( ( label != null ) && ( label.length() > 0 ) ) {
			buff.append( "\n");
			emitIndent();
			buff.append( "\t\tLabel=\"" + CFLibXmlUtil.formatXmlString( label ) + "\"" );
		}
		if( ( licenseName != null ) && ( licenseName.length() > 0 ) ) {
			buff.append( "\n");
			emitIndent();
			buff.append( "\t\tDefaultLicense=\"" + CFLibXmlUtil.formatXmlString( licenseName ) + "\"" );
		}
		buff.append( "\n");
		emitIndent();
		buff.append( "\t\tCopyrightHolder=\"" + CFLibXmlUtil.formatXmlString( copyrightHolder ) + "\"" );
		buff.append( "\n");
		emitIndent();
		buff.append( "\t\tCopyrightPeriod=\"" + CFLibXmlUtil.formatXmlString( copyrightPeriod ) + "\"" );
		buff.append( "\n");
		emitIndent();
		buff.append( "\t\tAuthorEMail=\"" + CFLibXmlUtil.formatXmlString( authorEMail ) + "\"" );
		buff.append( "\n");
		emitIndent();
		buff.append( "\t\tProjectURL=\"" + CFLibXmlUtil.formatXmlString( projectURL ) + "\"" );
		buff.append( "\n");
		emitIndent();
		buff.append( "\t\tPublishURI=\"" + CFLibXmlUtil.formatXmlString( publishURI ) + "\"" );
		buff.append( " >\n" );
		indent();


		List<ICFBamSchemaRefObj> schemaRefs = schemaDef.getOptionalComponentsSchemaRefs();
		if( schemaRefs.size() > 0 ) {
			String refModelName;
			String includeRoot;
			ICFBamSchemaRefObj schemaRef;
			ICFBamSchemaRefObj headSchemaRef = null;
			Iterator<ICFBamSchemaRefObj> iter = schemaRefs.iterator();
			while( ( headSchemaRef == null ) && iter.hasNext() ) {
				schemaRef = iter.next();
				if( schemaRef.getOptionalLookupPrev() == null ) {
					headSchemaRef = schemaRef;
				}
			}
			if( headSchemaRef == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"headSchemaRef" );
			}
			schemaRef = headSchemaRef;
			while( schemaRef != null ) {
				refModelName = schemaRef.getRequiredRefModelName();
				includeRoot = schemaRef.getRequiredIncludeRoot();
				buff.append( "\n" );
				emitIndent();
				buff.append( "<SchemaRef Name=\"" + CFLibXmlUtil.formatXmlString( schemaRef.getObjName() ) + "\"\n" );
				emitIndent();
				buff.append( "\t\tRefModelName=\"" + CFLibXmlUtil.formatXmlString( refModelName ) + "\"\n" );
				emitIndent();
				buff.append( "\t\tIncludeRoot=\"" + CFLibXmlUtil.formatXmlString( includeRoot ) + "\" />\n" );
				schemaRef = schemaRef.getOptionalLookupNext();
			}
		}

		emitTypes( schemaDef );
		emitTables( schemaDef );
		emitTableRelations( schemaDef );
		emitChains( schemaDef );

		Iterator<ICFBamTableObj> iter = alphabeticalTableList.iterator();
		ICFBamTableObj tableDef;
		while( iter.hasNext() ) {
			tableDef = iter.next();
			emitTableClearDeps( tableDef );
		}

		iter = alphabeticalTableList.iterator();
		while( iter.hasNext() ) {
			tableDef = iter.next();
			emitTableDelDeps( tableDef );
		}

		emitServerMethods( schemaDef );

		String text = schemaDef.getOptionalJSchemaObjImport();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeSchemaObjImport>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeSchemaObjImport>\n" );
		}

		text = schemaDef.getOptionalJSchemaObjInterface();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeSchemaObjInterface>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeSchemaObjInterface>\n" );
		}

		text = schemaDef.getOptionalJSchemaObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeSchemaObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeSchemaObjMembers>\n" );
		}

		text = schemaDef.getOptionalJSchemaObjImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeSchemaObjImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeSchemaObjImplementation>\n" );
		}

		text = schemaDef.getOptionalJDb2LUWSchemaObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeDb2LUWSchemaObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeDb2LUWSchemaObjMembers>\n" );
		}

		text = schemaDef.getOptionalJDb2LUWSchemaObjImpl();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeDb2LUWSchemaObjImpl>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeDb2LUWSchemaObjImpl>\n" );
		}

		text = schemaDef.getOptionalJDb2LUWSchemaObjImport();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeDb2LUWSchemaObjImport>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeDb2LUWSchemaObjImport>\n" );
		}

		text = schemaDef.getOptionalJMSSqlSchemaObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeMSSqlSchemaObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeMSSqlSchemaObjMembers>\n" );
		}

		text = schemaDef.getOptionalJMSSqlSchemaObjImpl();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeMSSqlSchemaObjImpl>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeMSSqlSchemaObjImpl>\n" );
		}

		text = schemaDef.getOptionalJMSSqlSchemaObjImport();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeMSSqlSchemaObjImport>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeMSSqlSchemaObjImport>\n" );
		}

		text = schemaDef.getOptionalJMySqlSchemaObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeMySqlSchemaObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeMySqlSchemaObjMembers>\n" );
		}

		text = schemaDef.getOptionalJMySqlSchemaObjImpl();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeMySqlSchemaObjImpl>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeMySqlSchemaObjImpl>\n" );
		}

		text = schemaDef.getOptionalJMySqlSchemaObjImport();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeMySqlSchemaObjImport>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeMySqlSchemaObjImport>\n" );
		}

		text = schemaDef.getOptionalJOracleSchemaObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeOracleSchemaObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeOracleSchemaObjMembers>\n" );
		}

		text = schemaDef.getOptionalJOracleSchemaObjImpl();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeOracleSchemaObjImpl>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeOracleSchemaObjImpl>\n" );
		}

		text = schemaDef.getOptionalJOracleSchemaObjImport();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeOracleSchemaObjImport>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeOracleSchemaObjImport>\n" );
		}

		text = schemaDef.getOptionalJPgSqlSchemaObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafePgSqlSchemaObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafePgSqlSchemaObjMembers>\n" );
		}

		text = schemaDef.getOptionalJPgSqlSchemaObjImpl();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafePgSqlSchemaObjImpl>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafePgSqlSchemaObjImpl>\n" );
		}

		text = schemaDef.getOptionalJPgSqlSchemaObjImport();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafePgSqlSchemaObjImport>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafePgSqlSchemaObjImport>\n" );
		}

		text = schemaDef.getOptionalJRamSchemaObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeRamSchemaObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeRamSchemaObjMembers>\n" );
		}

		text = schemaDef.getOptionalJRamSchemaObjImpl();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeRamSchemaObjImpl>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeRamSchemaObjImpl>\n" );
		}

		text = schemaDef.getOptionalJRamSchemaObjImport();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeRamSchemaObjImport>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeRamSchemaObjImport>\n" );
		}

		text = schemaDef.getOptionalJXMsgSchemaImport();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeXMsgSchemaImport>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeXMsgSchemaImport>\n" );
		}

		text = schemaDef.getOptionalJXMsgSchemaFormatters();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeXMsgSchemaFormatters>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeXMsgSchemaFormatters>\n" );
		}

		text = schemaDef.getOptionalJXMsgClientSchemaImport();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeXMsgClientSchemaImport>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeXMsgClientSchemaImport>\n" );
		}

		text = schemaDef.getOptionalJXMsgClientSchemaBody();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeXMsgClientSchemaBody>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeXMsgClientSchemaBody>\n" );
		}

		text = schemaDef.getOptionalJXMsgRqstSchemaBody();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeXMsgRqstSchemaBody>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeXMsgRqstSchemaBody>\n" );
		}

		text = schemaDef.getOptionalJXMsgRqstSchemaImport();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeXMsgRqstSchemaImport>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeXMsgRqstSchemaImport>\n" );
		}

		text = schemaDef.getOptionalJXMsgRqstSchemaWireParsers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeXMsgRqstSchemaWireParsers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeXMsgRqstSchemaWireParsers>\n" );
		}

		text = schemaDef.getOptionalJXMsgRqstSchemaXsdSpec();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeXMsgRqstSchemaXsdSpec>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeXMsgRqstSchemaXsdSpec>\n" );
		}

		text = schemaDef.getOptionalJXMsgRqstSchemaXsdElementList();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeXMsgRqstSchemaXsdElementList>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeXMsgRqstSchemaXsdElementList>\n" );
		}

		text = schemaDef.getOptionalJXMsgRspnSchemaBody();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeXMsgRspnSchemaBody>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeXMsgRspnSchemaBody>\n" );
		}

		text = schemaDef.getOptionalJXMsgRspnSchemaImport();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeXMsgRspnSchemaImport>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeXMsgRspnSchemaImport>\n" );
		}

		text = schemaDef.getOptionalJXMsgRspnSchemaWireParsers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeXMsgRspnSchemaWireParsers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeXMsgRspnSchemaWireParsers>\n" );
		}

		text = schemaDef.getOptionalJXMsgRspnSchemaXsdElementList();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeXMsgRspnSchemaXsdElementList>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeXMsgRspnSchemaXsdElementList>\n" );
		}

		text = schemaDef.getOptionalJXMsgRspnSchemaXsdSpec();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeXMsgRspnSchemaXsdSpec>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeXMsgRspnSchemaXsdSpec>\n" );
		}

		text = schemaDef.getOptionalCppSchemaObjInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusSchemaObjInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusSchemaObjInclude>\n" );
		}

		text = schemaDef.getOptionalCppSchemaObjInterface();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusSchemaObjInterface>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusSchemaObjInterface>\n" );
		}

		text = schemaDef.getOptionalCppSchemaObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusSchemaObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusSchemaObjMembers>\n" );
		}

		text = schemaDef.getOptionalCppSchemaObjImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusSchemaObjImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusSchemaObjImplementation>\n" );
		}

		text = schemaDef.getOptionalCppDb2LUWSchemaObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusDb2LUWSchemaObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusDb2LUWSchemaObjMembers>\n" );
		}

		text = schemaDef.getOptionalCppDb2LUWSchemaObjImpl();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusDb2LUWSchemaObjImpl>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusDb2LUWSchemaObjImpl>\n" );
		}

		text = schemaDef.getOptionalCppDb2LUWSchemaObjInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusDb2LUWSchemaObjInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusDb2LUWSchemaObjInclude>\n" );
		}

		text = schemaDef.getOptionalCppMSSqlSchemaObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusMSSqlSchemaObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusMSSqlSchemaObjMembers>\n" );
		}

		text = schemaDef.getOptionalCppMSSqlSchemaObjImpl();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusMSSqlSchemaObjImpl>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusMSSqlSchemaObjImpl>\n" );
		}

		text = schemaDef.getOptionalCppMSSqlSchemaObjInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusMSSqlSchemaObjInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusMSSqlSchemaObjInclude>\n" );
		}

		text = schemaDef.getOptionalCppMySqlSchemaObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusMySqlSchemaObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusMySqlSchemaObjMembers>\n" );
		}

		text = schemaDef.getOptionalCppMySqlSchemaObjImpl();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusMySqlSchemaObjImpl>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusMySqlSchemaObjImpl>\n" );
		}

		text = schemaDef.getOptionalCppMySqlSchemaObjInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusMySqlSchemaObjInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusMySqlSchemaObjInclude>\n" );
		}

		text = schemaDef.getOptionalCppOracleSchemaObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusOracleSchemaObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusOracleSchemaObjMembers>\n" );
		}

		text = schemaDef.getOptionalCppOracleSchemaObjImpl();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusOracleSchemaObjImpl>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusOracleSchemaObjImpl>\n" );
		}

		text = schemaDef.getOptionalCppOracleSchemaObjInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusOracleSchemaObjInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusOracleSchemaObjInclude>\n" );
		}

		text = schemaDef.getOptionalCppPgSqlSchemaObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusPgSqlSchemaObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusPgSqlSchemaObjMembers>\n" );
		}

		text = schemaDef.getOptionalCppPgSqlSchemaObjImpl();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusPgSqlSchemaObjImpl>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusPgSqlSchemaObjImpl>\n" );
		}

		text = schemaDef.getOptionalCppPgSqlSchemaObjInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusPgSqlSchemaObjInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusPgSqlSchemaObjInclude>\n" );
		}

		text = schemaDef.getOptionalCppRamSchemaObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusRamSchemaObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusRamSchemaObjMembers>\n" );
		}

		text = schemaDef.getOptionalCppRamSchemaObjImpl();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusRamSchemaObjImpl>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusRamSchemaObjImpl>\n" );
		}

		text = schemaDef.getOptionalCppRamSchemaObjInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusRamSchemaObjInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusRamSchemaObjInclude>\n" );
		}

		text = schemaDef.getOptionalCppXMsgSchemaInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusXMsgSchemaInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusXMsgSchemaInclude>\n" );
		}

		text = schemaDef.getOptionalCppXMsgSchemaFormatters();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusXMsgSchemaFormatters>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusXMsgSchemaFormatters>\n" );
		}

		text = schemaDef.getOptionalCppXMsgClientSchemaInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusXMsgClientSchemaInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusXMsgClientSchemaInclude>\n" );
		}

		text = schemaDef.getOptionalCppXMsgClientSchemaBody();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusXMsgClientSchemaBody>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusXMsgClientSchemaBody>\n" );
		}

		text = schemaDef.getOptionalCppXMsgRqstSchemaBody();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusXMsgRqstSchemaBody>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusXMsgRqstSchemaBody>\n" );
		}

		text = schemaDef.getOptionalCppXMsgRqstSchemaInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusXMsgRqstSchemaInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusXMsgRqstSchemaInclude>\n" );
		}

		text = schemaDef.getOptionalCppXMsgRqstSchemaWireParsers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusXMsgRqstSchemaWireParsers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusXMsgRqstSchemaWireParsers>\n" );
		}

		text = schemaDef.getOptionalCppXMsgRqstSchemaXsdSpec();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusXMsgRqstSchemaXsdSpec>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusXMsgRqstSchemaXsdSpec>\n" );
		}

		text = schemaDef.getOptionalCppXMsgRqstSchemaXsdElementList();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusXMsgRqstSchemaXsdElementList>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusXMsgRqstSchemaXsdElementList>\n" );
		}

		text = schemaDef.getOptionalCppXMsgRspnSchemaBody();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusXMsgRspnSchemaBody>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusXMsgRspnSchemaBody>\n" );
		}

		text = schemaDef.getOptionalCppXMsgRspnSchemaInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusXMsgRspnSchemaInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusXMsgRspnSchemaInclude>\n" );
		}

		text = schemaDef.getOptionalCppXMsgRspnSchemaWireParsers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusXMsgRspnSchemaWireParsers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusXMsgRspnSchemaWireParsers>\n" );
		}

		text = schemaDef.getOptionalCppXMsgRspnSchemaXsdElementList();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusXMsgRspnSchemaXsdElementList>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusXMsgRspnSchemaXsdElementList>\n" );
		}

		text = schemaDef.getOptionalCppXMsgRspnSchemaXsdSpec();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusXMsgRspnSchemaXsdSpec>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusXMsgRspnSchemaXsdSpec>\n" );
		}

		text = schemaDef.getOptionalHppSchemaObjInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusSchemaObjInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusSchemaObjInclude>\n" );
		}

		text = schemaDef.getOptionalHppSchemaObjInterface();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusSchemaObjInterface>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusSchemaObjInterface>\n" );
		}

		text = schemaDef.getOptionalHppSchemaObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusSchemaObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusSchemaObjMembers>\n" );
		}

		text = schemaDef.getOptionalHppSchemaObjImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusSchemaObjImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusSchemaObjImplementation>\n" );
		}

		text = schemaDef.getOptionalHppDb2LUWSchemaObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusDb2LUWSchemaObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusDb2LUWSchemaObjMembers>\n" );
		}

		text = schemaDef.getOptionalHppDb2LUWSchemaObjImpl();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusDb2LUWSchemaObjImpl>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusDb2LUWSchemaObjImpl>\n" );
		}

		text = schemaDef.getOptionalHppDb2LUWSchemaObjInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusDb2LUWSchemaObjInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusDb2LUWSchemaObjInclude>\n" );
		}

		text = schemaDef.getOptionalHppMSSqlSchemaObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusMSSqlSchemaObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusMSSqlSchemaObjMembers>\n" );
		}

		text = schemaDef.getOptionalHppMSSqlSchemaObjImpl();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusMSSqlSchemaObjImpl>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusMSSqlSchemaObjImpl>\n" );
		}

		text = schemaDef.getOptionalHppMSSqlSchemaObjInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusMSSqlSchemaObjInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusMSSqlSchemaObjInclude>\n" );
		}

		text = schemaDef.getOptionalHppMySqlSchemaObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusMySqlSchemaObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusMySqlSchemaObjMembers>\n" );
		}

		text = schemaDef.getOptionalHppMySqlSchemaObjImpl();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusMySqlSchemaObjImpl>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusMySqlSchemaObjImpl>\n" );
		}

		text = schemaDef.getOptionalHppMySqlSchemaObjInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusMySqlSchemaObjInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusMySqlSchemaObjInclude>\n" );
		}

		text = schemaDef.getOptionalHppOracleSchemaObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusOracleSchemaObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusOracleSchemaObjMembers>\n" );
		}

		text = schemaDef.getOptionalHppOracleSchemaObjImpl();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusOracleSchemaObjImpl>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusOracleSchemaObjImpl>\n" );
		}

		text = schemaDef.getOptionalHppOracleSchemaObjInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusOracleSchemaObjInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusOracleSchemaObjInclude>\n" );
		}

		text = schemaDef.getOptionalHppPgSqlSchemaObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusPgSqlSchemaObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusPgSqlSchemaObjMembers>\n" );
		}

		text = schemaDef.getOptionalHppPgSqlSchemaObjImpl();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusPgSqlSchemaObjImpl>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusPgSqlSchemaObjImpl>\n" );
		}

		text = schemaDef.getOptionalHppPgSqlSchemaObjInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusPgSqlSchemaObjInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusPgSqlSchemaObjInclude>\n" );
		}

		text = schemaDef.getOptionalHppRamSchemaObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusRamSchemaObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusRamSchemaObjMembers>\n" );
		}

		text = schemaDef.getOptionalHppRamSchemaObjImpl();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusRamSchemaObjImpl>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusRamSchemaObjImpl>\n" );
		}

		text = schemaDef.getOptionalHppRamSchemaObjInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusRamSchemaObjInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusRamSchemaObjInclude>\n" );
		}

		text = schemaDef.getOptionalHppXMsgSchemaInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusXMsgSchemaInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusXMsgSchemaInclude>\n" );
		}

		text = schemaDef.getOptionalHppXMsgSchemaFormatters();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusXMsgSchemaFormatters>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusXMsgSchemaFormatters>\n" );
		}

		text = schemaDef.getOptionalHppXMsgClientSchemaInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusXMsgClientSchemaInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusXMsgClientSchemaInclude>\n" );
		}

		text = schemaDef.getOptionalHppXMsgClientSchemaBody();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusXMsgClientSchemaBody>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusXMsgClientSchemaBody>\n" );
		}

		text = schemaDef.getOptionalHppXMsgRqstSchemaBody();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusXMsgRqstSchemaBody>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusXMsgRqstSchemaBody>\n" );
		}

		text = schemaDef.getOptionalHppXMsgRqstSchemaInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusXMsgRqstSchemaInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusXMsgRqstSchemaInclude>\n" );
		}

		text = schemaDef.getOptionalHppXMsgRqstSchemaWireParsers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusXMsgRqstSchemaWireParsers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusXMsgRqstSchemaWireParsers>\n" );
		}

		text = schemaDef.getOptionalHppXMsgRqstSchemaXsdSpec();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusXMsgRqstSchemaXsdSpec>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusXMsgRqstSchemaXsdSpec>\n" );
		}

		text = schemaDef.getOptionalHppXMsgRqstSchemaXsdElementList();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusXMsgRqstSchemaXsdElementList>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusXMsgRqstSchemaXsdElementList>\n" );
		}

		text = schemaDef.getOptionalHppXMsgRspnSchemaBody();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusXMsgRspnSchemaBody>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusXMsgRspnSchemaBody>\n" );
		}

		text = schemaDef.getOptionalHppXMsgRspnSchemaInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusXMsgRspnSchemaInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusXMsgRspnSchemaInclude>\n" );
		}

		text = schemaDef.getOptionalHppXMsgRspnSchemaWireParsers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusXMsgRspnSchemaWireParsers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusXMsgRspnSchemaWireParsers>\n" );
		}

		text = schemaDef.getOptionalHppXMsgRspnSchemaXsdElementList();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusXMsgRspnSchemaXsdElementList>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusXMsgRspnSchemaXsdElementList>\n" );
		}

		text = schemaDef.getOptionalHppXMsgRspnSchemaXsdSpec();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusXMsgRspnSchemaXsdSpec>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusXMsgRspnSchemaXsdSpec>\n" );
		}

		text = schemaDef.getOptionalCsSchemaObjUsing();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpSchemaObjUsing>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpSchemaObjUsing>\n" );
		}

		text = schemaDef.getOptionalCsSchemaObjInterface();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpSchemaObjInterface>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpSchemaObjInterface>\n" );
		}

		text = schemaDef.getOptionalCsSchemaObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpSchemaObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpSchemaObjMembers>\n" );
		}

		text = schemaDef.getOptionalCsSchemaObjImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpSchemaObjImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpSchemaObjImplementation>\n" );
		}

		text = schemaDef.getOptionalCsDb2LUWSchemaObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpDb2LUWSchemaObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpDb2LUWSchemaObjMembers>\n" );
		}

		text = schemaDef.getOptionalCsDb2LUWSchemaObjImpl();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpDb2LUWSchemaObjImpl>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpDb2LUWSchemaObjImpl>\n" );
		}

		text = schemaDef.getOptionalCsDb2LUWSchemaObjUsing();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpDb2LUWSchemaObjUsing>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpDb2LUWSchemaObjUsing>\n" );
		}

		text = schemaDef.getOptionalCsMSSqlSchemaObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpMSSqlSchemaObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpMSSqlSchemaObjMembers>\n" );
		}

		text = schemaDef.getOptionalCsMSSqlSchemaObjImpl();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpMSSqlSchemaObjImpl>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpMSSqlSchemaObjImpl>\n" );
		}

		text = schemaDef.getOptionalCsMSSqlSchemaObjUsing();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpMSSqlSchemaObjUsing>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpMSSqlSchemaObjUsing>\n" );
		}

		text = schemaDef.getOptionalCsMySqlSchemaObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpMySqlSchemaObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpMySqlSchemaObjMembers>\n" );
		}

		text = schemaDef.getOptionalCsMySqlSchemaObjImpl();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpMySqlSchemaObjImpl>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpMySqlSchemaObjImpl>\n" );
		}

		text = schemaDef.getOptionalCsMySqlSchemaObjUsing();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpMySqlSchemaObjUsing>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpMySqlSchemaObjUsing>\n" );
		}

		text = schemaDef.getOptionalCsOracleSchemaObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpOracleSchemaObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpOracleSchemaObjMembers>\n" );
		}

		text = schemaDef.getOptionalCsOracleSchemaObjImpl();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpOracleSchemaObjImpl>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpOracleSchemaObjImpl>\n" );
		}

		text = schemaDef.getOptionalCsOracleSchemaObjUsing();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpOracleSchemaObjUsing>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpOracleSchemaObjUsing>\n" );
		}

		text = schemaDef.getOptionalCsPgSqlSchemaObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpPgSqlSchemaObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpPgSqlSchemaObjMembers>\n" );
		}

		text = schemaDef.getOptionalCsPgSqlSchemaObjImpl();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpPgSqlSchemaObjImpl>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpPgSqlSchemaObjImpl>\n" );
		}

		text = schemaDef.getOptionalCsPgSqlSchemaObjUsing();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpPgSqlSchemaObjUsing>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpPgSqlSchemaObjUsing>\n" );
		}

		text = schemaDef.getOptionalCsRamSchemaObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpRamSchemaObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpRamSchemaObjMembers>\n" );
		}

		text = schemaDef.getOptionalCsRamSchemaObjImpl();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpRamSchemaObjImpl>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpRamSchemaObjImpl>\n" );
		}

		text = schemaDef.getOptionalCsRamSchemaObjUsing();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpRamSchemaObjUsing>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpRamSchemaObjUsing>\n" );
		}

		text = schemaDef.getOptionalCsXMsgSchemaUsing();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpXMsgSchemaUsing>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpXMsgSchemaUsing>\n" );
		}

		text = schemaDef.getOptionalCsXMsgSchemaFormatters();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpXMsgSchemaFormatters>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpXMsgSchemaFormatters>\n" );
		}

		text = schemaDef.getOptionalCsXMsgClientSchemaUsing();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpXMsgClientSchemaUsing>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpXMsgClientSchemaUsing>\n" );
		}

		text = schemaDef.getOptionalCsXMsgClientSchemaBody();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpXMsgClientSchemaBody>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpXMsgClientSchemaBody>\n" );
		}

		text = schemaDef.getOptionalCsXMsgRqstSchemaBody();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpXMsgRqstSchemaBody>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpXMsgRqstSchemaBody>\n" );
		}

		text = schemaDef.getOptionalCsXMsgRqstSchemaUsing();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpXMsgRqstSchemaUsing>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpXMsgRqstSchemaUsing>\n" );
		}

		text = schemaDef.getOptionalCsXMsgRqstSchemaWireParsers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpXMsgRqstSchemaWireParsers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpXMsgRqstSchemaWireParsers>\n" );
		}

		text = schemaDef.getOptionalCsXMsgRqstSchemaXsdSpec();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpXMsgRqstSchemaXsdSpec>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpXMsgRqstSchemaXsdSpec>\n" );
		}

		text = schemaDef.getOptionalCsXMsgRqstSchemaXsdElementList();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpXMsgRqstSchemaXsdElementList>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpXMsgRqstSchemaXsdElementList>\n" );
		}

		text = schemaDef.getOptionalCsXMsgRspnSchemaBody();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpXMsgRspnSchemaBody>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpXMsgRspnSchemaBody>\n" );
		}

		text = schemaDef.getOptionalCsXMsgRspnSchemaUsing();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpXMsgRspnSchemaUsing>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpXMsgRspnSchemaUsing>\n" );
		}

		text = schemaDef.getOptionalCsXMsgRspnSchemaWireParsers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpXMsgRspnSchemaWireParsers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpXMsgRspnSchemaWireParsers>\n" );
		}

		text = schemaDef.getOptionalCsXMsgRspnSchemaXsdElementList();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpXMsgRspnSchemaXsdElementList>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpXMsgRspnSchemaXsdElementList>\n" );
		}

		text = schemaDef.getOptionalCsXMsgRspnSchemaXsdSpec();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpXMsgRspnSchemaXsdSpec>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpXMsgRspnSchemaXsdSpec>\n" );
		}

		dedent();
		emitIndent();
		buff.append( "</SchemaDef>\n" );

		indentLevel = saveIndent;

		emitEndHierarchy( scope );
	}

	protected void emitTypes( ICFBamSchemaDefObj schemaDef ) {
		ICFBamValueObj typeDef;
		Iterator<ICFBamValueObj> iterTypes = alphabeticalTypeList.iterator();
		while( iterTypes.hasNext() ) {
			typeDef = iterTypes.next();
			if( ! typeMap.containsKey( typeDef.getPKey() ) ) {
				emitTypeDef( typeDef );
			}
		}
	}

	protected void emitTables( ICFBamSchemaDefObj schemaDef ) {
		ICFBamRelationObj relation;
		ICFBamTableObj table;
		Iterator<ICFBamTableObj> tableIter = alphabeticalTableList.iterator();
		boolean emittedForAnyTables = true;
		boolean requirementsSatisfied;
		Iterator<ICFBamRelationObj> iterRelation;
		ICFBamTableObj qualifyingTable;
		Iterator<ICFBamValueObj> iterCol;
		ICFBamValueObj col;
		ICFBamTableColObj tableCol;
		ICFBamValueObj tableColType;
		while( emittedForAnyTables ) {
			emittedForAnyTables = false;
			tableIter = alphabeticalTableList.iterator();
			while( tableIter.hasNext() ) {
				table = tableIter.next();
				if( ! tableMap.containsKey( table.getPKey() ) ) {
					requirementsSatisfied = true;
					qualifyingTable = table.getOptionalLookupQualTable();
					if( qualifyingTable != null ) {
						requirementsSatisfied = tableMap.containsKey( qualifyingTable.getPKey() );
					}
					if( requirementsSatisfied ) {
						boolean stillHaventFoundSuperclass = true;
						iterRelation = table.getOptionalComponentsRelation().iterator();
						while( stillHaventFoundSuperclass && iterRelation.hasNext() ) {
							relation = iterRelation.next();
							if( relation.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Superclass ) {
								stillHaventFoundSuperclass = false;
								requirementsSatisfied = tableMap.containsKey( relation.getRequiredLookupToIndex().getRequiredContainerTable().getPKey() );
							}
						}
					}
					if( requirementsSatisfied ) {
						iterCol = table.getOptionalComponentsColumns().iterator();
						while( requirementsSatisfied && iterCol.hasNext() ) {
							col = iterCol.next();
							if( col instanceof ICFBamTableColObj ) {
								tableCol = (ICFBamTableColObj)col;
								tableColType = tableCol.getRequiredParentDataType();
								requirementsSatisfied = typeMap.containsKey( tableColType.getPKey() );
							}
						}
					}
					if( requirementsSatisfied ) {
						emittedForAnyTables = true;
						tableMap.put( table.getPKey(), table );
						emitTable( table );
						emitTypes( schemaDef );
					}
				}
			}
		}
	}

	protected void emitTable( ICFBamTableObj table ) {
		final String S_ProcName = "emitTable";
		buff.append( "\n" );
		emitIndent();
		buff.append( "<Table Name=\"" + CFLibXmlUtil.formatXmlString( table.getObjName() ) + "\"" );
		String tableClassCode = table.getRequiredTableClassCode();
		buff.append( "\n" );
		emitIndent();
		buff.append( "\t\tTableClassCode=\"" + CFLibXmlUtil.formatXmlString( tableClassCode ) + "\"" );
		String dbName = table.getOptionalDbName();
		if( ( dbName != null ) && ( dbName.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tDbName=\"" + CFLibXmlUtil.formatXmlString( dbName ) + "\"" );
		}
		String shortName = table.getOptionalShortName();
		if( ( shortName != null ) && ( shortName.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tShortName=\"" + CFLibXmlUtil.formatXmlString( shortName ) + "\"" );
		}
		String label = table.getOptionalLabel();
		if( ( label != null ) && ( label.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tLabel=\"" + CFLibXmlUtil.formatXmlString( label ) + "\"" );
		}
		String description = table.getOptionalDescription();
		if( ( description != null ) && ( description.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tDescription=\"" + CFLibXmlUtil.formatXmlString( description ) + "\"" );
		}
		String shortDescription = table.getOptionalShortDescription();
		if( ( shortDescription != null ) && ( shortDescription.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tShortDescription=\"" + CFLibXmlUtil.formatXmlString( shortDescription ) + "\"" );
		}
		buff.append( "\n" );
		emitIndent();
		buff.append( "\t\tIsInstantiable=\""
			+ ( table.getRequiredIsInstantiable()
					? "true"
					: "false" )
			+ "\"" );
		buff.append( "\n" );
		emitIndent();
		buff.append( "\t\tHasHistory=\""
			+ ( table.getRequiredHasHistory()
					? "true"
					: "false" )
			+ "\"" );
		buff.append( "\n" );
		emitIndent();
		buff.append( "\t\tHasAuditColumns=\""
			+ ( table.getRequiredHasAuditColumns()
					? "true"
					: "false" )
			+ "\"" );
		if( table.getRequiredPageData() ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tPageData=\"true\"" );
		}
//		ICFBamIndexObj primaryIndex = table.getOptionalLookupPrimaryIndex();
//		if( primaryIndex != null ) {
//			buff.append( "\n" );
//			emitIndent();
//			buff.append( "\t\tPrimaryIndex=\"" + CFLibXmlUtil.formatXmlString( primaryIndex.getObjName() ) + "\"" );
//		}
		ICFBamIndexObj lookupIndex = table.getOptionalLookupLookupIndex();
		if( lookupIndex != null ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tLookupIndex=\"" + CFLibXmlUtil.formatXmlString( lookupIndex.getObjName() ) + "\"" );
		}
		ICFBamIndexObj altIndex = table.getOptionalLookupAltIndex();
		if( altIndex != null ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tAltIndex=\"" + CFLibXmlUtil.formatXmlString( altIndex.getObjName() ) + "\"" );
		}
		ICFBamTableObj qualifyingTable = table.getOptionalLookupQualTable();
		if( qualifyingTable != null ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tQualTable=\"" + CFLibXmlUtil.formatXmlString( qualifyingTable.getObjName() ) + "\"" );
		}
		ICFBamSchema.LoaderBehaviourEnum loaderBehaviour = table.getRequiredLoaderBehaviour();
		buff.append( "\n");
		emitIndent();
		buff.append( "\t\tLoaderBehaviour=\"" + loaderBehaviour.toString() + "\"" );
		ICFBamSchema.SecScopeEnum securityScope = table.getRequiredSecScope();
		buff.append( "\n");
		emitIndent();
		buff.append( "\t\tSecScope=\"" + securityScope.toString() + "\"" );
		buff.append( " >\n" );
		indent();

		ICFBamValueObj col;
		Iterator<ICFBamValueObj> iterCol = table.getOptionalComponentsColumns().iterator();
		ICFBamValueObj headCol = null;
		while( ( headCol == null ) && ( iterCol.hasNext() ) ) {
			col = iterCol.next();
			if( col.getOptionalLookupPrev() == null ) {
				headCol = col;
			}
		}
		if( headCol == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				0,
				"headCol" );
		}
		col = headCol;
		while( col != null ) {
			emitColumn( col );
			col = col.getOptionalLookupNext();
		}

		ICFBamIndexObj index;
		Iterator<ICFBamIndexObj> iterIndex = table.getOptionalComponentsIndex().iterator();
		while( iterIndex.hasNext() ) {
			index = iterIndex.next();
			emitIndex( index, index == table.getOptionalLookupPrimaryIndex() );
		}

		boolean stillHaventEmittedSuperclass = true;
		ICFBamRelationObj relation;
		Iterator<ICFBamRelationObj> iterRelation = table.getOptionalComponentsRelation().iterator();
		while( stillHaventEmittedSuperclass && iterRelation.hasNext() ) {
			relation = iterRelation.next();
			if( relation.getRequiredRelationType() == ICFBamSchema.RelationTypeEnum.Superclass ) {
				stillHaventEmittedSuperclass = false;
				emitActualRelation( relation );
			}
		}

		String text = table.getOptionalJObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeObjMembers>\n" );
		}

		text = table.getOptionalJObjInterface();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeObjInterface>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeObjInterface>\n" );
		}

		text = table.getOptionalJObjImport();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeObjImport>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeObjImport>\n" );
		}

		text = table.getOptionalJObjImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeObjImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeObjImplementation>\n" );
		}

		text = table.getOptionalJEditObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeEditObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeEditObjMembers>\n" );
		}

		text = table.getOptionalJEditObjInterface();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeEditObjInterface>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeEditObjInterface>\n" );
		}

		text = table.getOptionalJEditObjImport();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeEditObjImport>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeEditObjImport>\n" );
		}

		text = table.getOptionalJEditObjImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeEditObjImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeEditObjImplementation>\n" );
		}

		text = table.getOptionalJTableImport();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeTableImport>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeTableImport>\n" );
		}

		text = table.getOptionalJTableMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeTableMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeTableMembers>\n" );
		}

		text = table.getOptionalJTableInterface();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeTableInterface>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeTableInterface>\n" );
		}

		text = table.getOptionalJTableImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeTableImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeTableImplementation>\n" );
		}

		text = table.getOptionalJTableObjImport();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeTableObjImport>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeTableObjImport>\n" );
		}

		text = table.getOptionalJTableObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeTableObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeTableObjMembers>\n" );
		}

		text = table.getOptionalJTableObjInterface();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeTableObjInterface>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeTableObjInterface>\n" );
		}

		text = table.getOptionalJTableObjImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeTableObjImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeTableObjImplementation>\n" );
		}

		text = table.getOptionalJDb2LUWTableImport();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeDb2LUWTableImport>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeDb2LUWTableImport>\n" );
		}

		text = table.getOptionalJDb2LUWTableMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeDb2LUWTableMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeDb2LUWTableMembers>\n" );
		}

		text = table.getOptionalJDb2LUWTableImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeDb2LUWTableImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeDb2LUWTableImplementation>\n" );
		}

		text = table.getOptionalJMSSqlTableImport();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeMSSqlTableImport>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeMSSqlTableImport>\n" );
		}

		text = table.getOptionalJMSSqlTableMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeMSSqlTableMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeMSSqlTableMembers>\n" );
		}

		text = table.getOptionalJMSSqlTableImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeMSSqlTableImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeMSSqlTableImplementation>\n" );
		}

		text = table.getOptionalJMySqlTableImport();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeMySqlTableImport>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeMySqlTableImport>\n" );
		}

		text = table.getOptionalJMySqlTableMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeMySqlTableMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeMySqlTableMembers>\n" );
		}

		text = table.getOptionalJMySqlTableImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeMySqlTableImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeMySqlTableImplementation>\n" );
		}

		text = table.getOptionalJOracleTableImport();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeOracleTableImport>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeOracleTableImport>\n" );
		}

		text = table.getOptionalJOracleTableMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeOracleTableMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeOracleTableMembers>\n" );
		}

		text = table.getOptionalJOracleTableImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeOracleTableImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeOracleTableImplementation>\n" );
		}

		text = table.getOptionalJPgSqlTableImport();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafePgSqlTableImport>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafePgSqlTableImport>\n" );
		}

		text = table.getOptionalJPgSqlTableMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafePgSqlTableMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafePgSqlTableMembers>\n" );
		}

		text = table.getOptionalJPgSqlTableImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafePgSqlTableImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafePgSqlTableImplementation>\n" );
		}

		text = table.getOptionalJRamTableImport();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeRamTableImport>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeRamTableImport>\n" );
		}

		text = table.getOptionalJRamTableMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeRamTableMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeRamTableMembers>\n" );
		}

		text = table.getOptionalJRamTableImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeRamTableImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeRamTableImplementation>\n" );
		}

		text = table.getOptionalJSaxLoaderImport();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeSaxLoaderImport>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeSaxLoaderImport>\n" );
		}

		text = table.getOptionalJSaxLoaderStartElement();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeSaxLoaderStartElement>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeSaxLoaderStartElement>\n" );
		}

		text = table.getOptionalJSaxLoaderEndElement();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeSaxLoaderEndElement>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeSaxLoaderEndElement>\n" );
		}

		text = table.getOptionalJXMsgTableImport();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeXMsgTableImport>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeXMsgTableImport>\n" );
		}

		text = table.getOptionalJXMsgTableFormatters();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeXMsgTableFormatters>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeXMsgTableFormatters>\n" );
		}

		text = table.getOptionalJXMsgRqstTableImport();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeXMsgRqstTableImport>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeXMsgRqstTableImport>\n" );
		}

		text = table.getOptionalJXMsgRspnTableImport();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeXMsgRspnTableImport>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeXMsgRspnTableImport>\n" );
		}

		text = table.getOptionalJXMsgClientTableImport();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeXMsgClientTableImport>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeXMsgClientTableImport>\n" );
		}

		text = table.getOptionalJXMsgRqstTableBody();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeXMsgRqstTableBody>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeXMsgRqstTableBody>\n" );
		}

		text = table.getOptionalJXMsgRspnTableBody();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeXMsgRspnTableBody>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeXMsgRspnTableBody>\n" );
		}

		text = table.getOptionalJXMsgClientTableBody();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CafeXMsgClientTableBody>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CafeXMsgClientTableBody>\n" );
		}

		text = table.getOptionalCppObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusObjMembers>\n" );
		}

		text = table.getOptionalCppObjInterface();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusObjInterface>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusObjInterface>\n" );
		}

		text = table.getOptionalCppObjInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusObjInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusObjInclude>\n" );
		}

		text = table.getOptionalCppObjImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusObjImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusObjImplementation>\n" );
		}

		text = table.getOptionalCppEditObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusEditObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusEditObjMembers>\n" );
		}

		text = table.getOptionalCppEditObjInterface();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusEditObjInterface>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusEditObjInterface>\n" );
		}

		text = table.getOptionalCppEditObjInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusEditObjInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusEditObjInclude>\n" );
		}

		text = table.getOptionalCppEditObjImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusEditObjImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusEditObjImplementation>\n" );
		}

		text = table.getOptionalCppTableInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusTableInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusTableInclude>\n" );
		}

		text = table.getOptionalCppTableMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusTableMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusTableMembers>\n" );
		}

		text = table.getOptionalCppTableInterface();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusTableInterface>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusTableInterface>\n" );
		}

		text = table.getOptionalCppTableImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusTableImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusTableImplementation>\n" );
		}

		text = table.getOptionalCppTableObjInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusTableObjInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusTableObjInclude>\n" );
		}

		text = table.getOptionalCppTableObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusTableObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusTableObjMembers>\n" );
		}

		text = table.getOptionalCppTableObjInterface();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusTableObjInterface>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusTableObjInterface>\n" );
		}

		text = table.getOptionalCppTableObjImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusTableObjImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusTableObjImplementation>\n" );
		}

		text = table.getOptionalCppDb2LUWTableInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusDb2LUWTableInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusDb2LUWTableInclude>\n" );
		}

		text = table.getOptionalCppDb2LUWTableMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusDb2LUWTableMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusDb2LUWTableMembers>\n" );
		}

		text = table.getOptionalCppDb2LUWTableImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusDb2LUWTableImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusDb2LUWTableImplementation>\n" );
		}

		text = table.getOptionalCppMSSqlTableInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusMSSqlTableInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusMSSqlTableInclude>\n" );
		}

		text = table.getOptionalCppMSSqlTableMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusMSSqlTableMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusMSSqlTableMembers>\n" );
		}

		text = table.getOptionalCppMSSqlTableImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusMSSqlTableImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusMSSqlTableImplementation>\n" );
		}

		text = table.getOptionalCppMySqlTableInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusMySqlTableInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusMySqlTableInclude>\n" );
		}

		text = table.getOptionalCppMySqlTableMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusMySqlTableMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusMySqlTableMembers>\n" );
		}

		text = table.getOptionalCppMySqlTableImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusMySqlTableImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusMySqlTableImplementation>\n" );
		}

		text = table.getOptionalCppOracleTableInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusOracleTableInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusOracleTableInclude>\n" );
		}

		text = table.getOptionalCppOracleTableMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusOracleTableMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusOracleTableMembers>\n" );
		}

		text = table.getOptionalCppOracleTableImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusOracleTableImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusOracleTableImplementation>\n" );
		}

		text = table.getOptionalCppPgSqlTableInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusPgSqlTableInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusPgSqlTableInclude>\n" );
		}

		text = table.getOptionalCppPgSqlTableMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusPgSqlTableMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusPgSqlTableMembers>\n" );
		}

		text = table.getOptionalCppPgSqlTableImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusPgSqlTableImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusPgSqlTableImplementation>\n" );
		}

		text = table.getOptionalCppRamTableInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusRamTableInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusRamTableInclude>\n" );
		}

		text = table.getOptionalCppRamTableMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusRamTableMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusRamTableMembers>\n" );
		}

		text = table.getOptionalCppRamTableImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusRamTableImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusRamTableImplementation>\n" );
		}

		text = table.getOptionalCppSaxLoaderInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusSaxLoaderInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusSaxLoaderInclude>\n" );
		}

		text = table.getOptionalCppSaxLoaderStartElement();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusSaxLoaderStartElement>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusSaxLoaderStartElement>\n" );
		}

		text = table.getOptionalCppSaxLoaderEndElement();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusSaxLoaderEndElement>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusSaxLoaderEndElement>\n" );
		}

		text = table.getOptionalCppXMsgTableInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusXMsgTableInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusXMsgTableInclude>\n" );
		}

		text = table.getOptionalCppXMsgTableFormatters();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusXMsgTableFormatters>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusXMsgTableFormatters>\n" );
		}

		text = table.getOptionalCppXMsgRqstTableInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusXMsgRqstTableInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusXMsgRqstTableInclude>\n" );
		}

		text = table.getOptionalCppXMsgRspnTableInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusXMsgRspnTableInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusXMsgRspnTableInclude>\n" );
		}

		text = table.getOptionalCppXMsgClientTableInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusXMsgClientTableInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusXMsgClientTableInclude>\n" );
		}

		text = table.getOptionalCppXMsgRqstTableBody();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusXMsgRqstTableBody>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusXMsgRqstTableBody>\n" );
		}

		text = table.getOptionalCppXMsgRspnTableBody();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusXMsgRspnTableBody>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusXMsgRspnTableBody>\n" );
		}

		text = table.getOptionalCppXMsgClientTableBody();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CPlusXMsgClientTableBody>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CPlusXMsgClientTableBody>\n" );
		}

		text = table.getOptionalHppObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusObjMembers>\n" );
		}

		text = table.getOptionalHppObjInterface();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusObjInterface>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusObjInterface>\n" );
		}

		text = table.getOptionalHppObjInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusObjInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusObjInclude>\n" );
		}

		text = table.getOptionalHppObjImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusObjImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusObjImplementation>\n" );
		}

		text = table.getOptionalHppEditObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusEditObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusEditObjMembers>\n" );
		}

		text = table.getOptionalHppEditObjInterface();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusEditObjInterface>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusEditObjInterface>\n" );
		}

		text = table.getOptionalHppEditObjInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusEditObjInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusEditObjInclude>\n" );
		}

		text = table.getOptionalHppEditObjImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusEditObjImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusEditObjImplementation>\n" );
		}

		text = table.getOptionalHppTableInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusTableInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusTableInclude>\n" );
		}

		text = table.getOptionalHppTableMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusTableMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusTableMembers>\n" );
		}

		text = table.getOptionalHppTableInterface();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusTableInterface>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusTableInterface>\n" );
		}

		text = table.getOptionalHppTableImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusTableImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusTableImplementation>\n" );
		}

		text = table.getOptionalHppTableObjInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusTableObjInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusTableObjInclude>\n" );
		}

		text = table.getOptionalHppTableObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusTableObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusTableObjMembers>\n" );
		}

		text = table.getOptionalHppTableObjInterface();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusTableObjInterface>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusTableObjInterface>\n" );
		}

		text = table.getOptionalHppTableObjImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusTableObjImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusTableObjImplementation>\n" );
		}

		text = table.getOptionalHppDb2LUWTableInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusDb2LUWTableInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusDb2LUWTableInclude>\n" );
		}

		text = table.getOptionalHppDb2LUWTableMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusDb2LUWTableMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusDb2LUWTableMembers>\n" );
		}

		text = table.getOptionalHppDb2LUWTableImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusDb2LUWTableImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusDb2LUWTableImplementation>\n" );
		}

		text = table.getOptionalHppMSSqlTableInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusMSSqlTableInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusMSSqlTableInclude>\n" );
		}

		text = table.getOptionalHppMSSqlTableMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusMSSqlTableMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusMSSqlTableMembers>\n" );
		}

		text = table.getOptionalHppMSSqlTableImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusMSSqlTableImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusMSSqlTableImplementation>\n" );
		}

		text = table.getOptionalHppMySqlTableInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusMySqlTableInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusMySqlTableInclude>\n" );
		}

		text = table.getOptionalHppMySqlTableMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusMySqlTableMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusMySqlTableMembers>\n" );
		}

		text = table.getOptionalHppMySqlTableImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusMySqlTableImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusMySqlTableImplementation>\n" );
		}

		text = table.getOptionalHppOracleTableInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusOracleTableInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusOracleTableInclude>\n" );
		}

		text = table.getOptionalHppOracleTableMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusOracleTableMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusOracleTableMembers>\n" );
		}

		text = table.getOptionalHppOracleTableImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusOracleTableImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusOracleTableImplementation>\n" );
		}

		text = table.getOptionalHppPgSqlTableInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusPgSqlTableInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusPgSqlTableInclude>\n" );
		}

		text = table.getOptionalHppPgSqlTableMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusPgSqlTableMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusPgSqlTableMembers>\n" );
		}

		text = table.getOptionalHppPgSqlTableImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusPgSqlTableImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusPgSqlTableImplementation>\n" );
		}

		text = table.getOptionalHppRamTableInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusRamTableInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusRamTableInclude>\n" );
		}

		text = table.getOptionalHppRamTableMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusRamTableMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusRamTableMembers>\n" );
		}

		text = table.getOptionalHppRamTableImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusRamTableImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusRamTableImplementation>\n" );
		}

		text = table.getOptionalHppSaxLoaderInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusSaxLoaderInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusSaxLoaderInclude>\n" );
		}

		text = table.getOptionalHppSaxLoaderStartElement();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusSaxLoaderStartElement>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusSaxLoaderStartElement>\n" );
		}

		text = table.getOptionalHppSaxLoaderEndElement();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusSaxLoaderEndElement>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusSaxLoaderEndElement>\n" );
		}

		text = table.getOptionalHppXMsgTableInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusXMsgTableInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusXMsgTableInclude>\n" );
		}

		text = table.getOptionalHppXMsgTableFormatters();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusXMsgTableFormatters>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusXMsgTableFormatters>\n" );
		}

		text = table.getOptionalHppXMsgRqstTableInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusXMsgRqstTableInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusXMsgRqstTableInclude>\n" );
		}

		text = table.getOptionalHppXMsgRspnTableInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusXMsgRspnTableInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusXMsgRspnTableInclude>\n" );
		}

		text = table.getOptionalHppXMsgClientTableInclude();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusXMsgClientTableInclude>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusXMsgClientTableInclude>\n" );
		}

		text = table.getOptionalHppXMsgRqstTableBody();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusXMsgRqstTableBody>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusXMsgRqstTableBody>\n" );
		}

		text = table.getOptionalHppXMsgRspnTableBody();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusXMsgRspnTableBody>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusXMsgRspnTableBody>\n" );
		}

		text = table.getOptionalHppXMsgClientTableBody();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<HPlusXMsgClientTableBody>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</HPlusXMsgClientTableBody>\n" );
		}

		text = table.getOptionalCsObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpObjMembers>\n" );
		}

		text = table.getOptionalCsObjInterface();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpObjInterface>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpObjInterface>\n" );
		}

		text = table.getOptionalCsObjUsing();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpObjUsing>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpObjUsing>\n" );
		}

		text = table.getOptionalCsObjImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpObjImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpObjImplementation>\n" );
		}

		text = table.getOptionalCsEditObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpEditObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpEditObjMembers>\n" );
		}

		text = table.getOptionalCsEditObjInterface();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpEditObjInterface>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpEditObjInterface>\n" );
		}

		text = table.getOptionalCsEditObjUsing();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpEditObjUsing>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpEditObjUsing>\n" );
		}

		text = table.getOptionalCsEditObjImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpEditObjImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpEditObjImplementation>\n" );
		}

		text = table.getOptionalCsTableUsing();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpTableUsing>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpTableUsing>\n" );
		}

		text = table.getOptionalCsTableMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpTableMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpTableMembers>\n" );
		}

		text = table.getOptionalCsTableInterface();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpTableInterface>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpTableInterface>\n" );
		}

		text = table.getOptionalCsTableImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpTableImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpTableImplementation>\n" );
		}

		text = table.getOptionalCsTableObjUsing();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpTableObjUsing>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpTableObjUsing>\n" );
		}

		text = table.getOptionalCsTableObjMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpTableObjMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpTableObjMembers>\n" );
		}

		text = table.getOptionalCsTableObjInterface();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpTableObjInterface>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpTableObjInterface>\n" );
		}

		text = table.getOptionalCsTableObjImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpTableObjImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpTableObjImplementation>\n" );
		}

		text = table.getOptionalCsDb2LUWTableUsing();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpDb2LUWTableUsing>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpDb2LUWTableUsing>\n" );
		}

		text = table.getOptionalCsDb2LUWTableMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpDb2LUWTableMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpDb2LUWTableMembers>\n" );
		}

		text = table.getOptionalCsDb2LUWTableImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpDb2LUWTableImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpDb2LUWTableImplementation>\n" );
		}

		text = table.getOptionalCsMSSqlTableUsing();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpMSSqlTableUsing>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpMSSqlTableUsing>\n" );
		}

		text = table.getOptionalCsMSSqlTableMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpMSSqlTableMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpMSSqlTableMembers>\n" );
		}

		text = table.getOptionalCsMSSqlTableImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpMSSqlTableImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpMSSqlTableImplementation>\n" );
		}

		text = table.getOptionalCsMySqlTableUsing();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpMySqlTableUsing>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpMySqlTableUsing>\n" );
		}

		text = table.getOptionalCsMySqlTableMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpMySqlTableMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpMySqlTableMembers>\n" );
		}

		text = table.getOptionalCsMySqlTableImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpMySqlTableImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpMySqlTableImplementation>\n" );
		}

		text = table.getOptionalCsOracleTableUsing();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpOracleTableUsing>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpOracleTableUsing>\n" );
		}

		text = table.getOptionalCsOracleTableMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpOracleTableMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpOracleTableMembers>\n" );
		}

		text = table.getOptionalCsOracleTableImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpOracleTableImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpOracleTableImplementation>\n" );
		}

		text = table.getOptionalCsPgSqlTableUsing();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpPgSqlTableUsing>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpPgSqlTableUsing>\n" );
		}

		text = table.getOptionalCsPgSqlTableMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpPgSqlTableMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpPgSqlTableMembers>\n" );
		}

		text = table.getOptionalCsPgSqlTableImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpPgSqlTableImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpPgSqlTableImplementation>\n" );
		}

		text = table.getOptionalCsRamTableUsing();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpRamTableUsing>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpRamTableUsing>\n" );
		}

		text = table.getOptionalCsRamTableMembers();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpRamTableMembers>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpRamTableMembers>\n" );
		}

		text = table.getOptionalCsRamTableImplementation();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpRamTableImplementation>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpRamTableImplementation>\n" );
		}

		text = table.getOptionalCsSaxLoaderUsing();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpSaxLoaderUsing>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpSaxLoaderUsing>\n" );
		}

		text = table.getOptionalCsSaxLoaderStartElement();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpSaxLoaderStartElement>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpSaxLoaderStartElement>\n" );
		}

		text = table.getOptionalCsSaxLoaderEndElement();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpSaxLoaderEndElement>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpSaxLoaderEndElement>\n" );
		}

		text = table.getOptionalCsXMsgTableUsing();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpXMsgTableUsing>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpXMsgTableUsing>\n" );
		}

		text = table.getOptionalCsXMsgTableFormatters();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpXMsgTableFormatters>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpXMsgTableFormatters>\n" );
		}

		text = table.getOptionalCsXMsgRqstTableUsing();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpXMsgRqstTableUsing>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpXMsgRqstTableUsing>\n" );
		}

		text = table.getOptionalCsXMsgRspnTableUsing();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpXMsgRspnTableUsing>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpXMsgRspnTableUsing>\n" );
		}

		text = table.getOptionalCsXMsgClientTableUsing();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpXMsgClientTableUsing>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpXMsgClientTableUsing>\n" );
		}

		text = table.getOptionalCsXMsgRqstTableBody();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpXMsgRqstTableBody>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpXMsgRqstTableBody>\n" );
		}

		text = table.getOptionalCsXMsgRspnTableBody();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpXMsgRspnTableBody>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpXMsgRspnTableBody>\n" );
		}

		text = table.getOptionalCsXMsgClientTableBody();
		if( ( text != null ) && ( text.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "<CSharpXMsgClientTableBody>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( text ) + "</CSharpXMsgClientTableBody>\n" );
		}

		dedent();
		emitIndent();
		buff.append( "</Table>\n" );
	}

	protected void emitTableRelations( ICFBamSchemaDefObj schemaDef ) {

		HashMap<CFBamScopePKey,ICFBamRelationObj> relationMap = new HashMap<CFBamScopePKey,ICFBamRelationObj>();

		ICFBamRelationObj relation;
		ICFBamTableObj table;
		Iterator<ICFBamRelationObj> relationIter;
		Iterator<ICFBamTableObj> tableIter = alphabeticalTableList.iterator();

		// Prime the "already emitted" hash map with imported/referenced relations
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			relationIter = table.getChildrenRelations().iterator();
			while( relationIter.hasNext() ) {
				relation = relationIter.next();
				if( relation.getOptionalLookupDefSchema() != null ) {
					relationMap.put( relation.getPKey(), relation );
				}
			}
		}

		boolean emittedForAnyTables = true;
		boolean emittedAnyForTable;
		boolean requirementsSatisfied;
		ICFBamRelationObj narrowedRelation;
		Iterator<ICFBamPopTopDepObj> topDepIter;
		ICFBamPopTopDepObj topDep;
		Iterator<ICFBamPopSubDep1Obj> subDep1Iter;
		ICFBamPopSubDep1Obj subDep1;
		Iterator<ICFBamPopSubDep2Obj> subDep2Iter;
		ICFBamPopSubDep2Obj subDep2;
		Iterator<ICFBamPopSubDep3Obj> subDep3Iter;
		ICFBamPopSubDep3Obj subDep3;

		while( emittedForAnyTables ) {
			emittedForAnyTables = false;
			tableIter = alphabeticalTableList.iterator();
			while( tableIter.hasNext() ) {
				table = tableIter.next();
				emittedAnyForTable = false;
				relationIter = table.getChildrenRelations().iterator();
				while( relationIter.hasNext() ) {
					relation = relationIter.next();
					if( ( null == relation.getOptionalLookupDefSchema() )
						&& ( relation.getRequiredRelationType() != ICFBamSchema.RelationTypeEnum.Superclass )
						&& ( ! relationMap.containsKey( relation.getPKey() ) ) )
					{
						requirementsSatisfied = true;
						narrowedRelation = relation.getOptionalLookupNarrowed();
						if( narrowedRelation != null ) {
							requirementsSatisfied = relationMap.containsKey( narrowedRelation.getPKey() );
						}
						if( requirementsSatisfied ) {
							topDepIter = relation.getOptionalComponentsPopDep().iterator();
							while( requirementsSatisfied && topDepIter.hasNext() ) {
								topDep = topDepIter.next();
								if( ! relationMap.containsKey( topDep.getRequiredLookupRelation().getPKey() ) ) {
									requirementsSatisfied = false;
								}
								else {
									subDep1Iter = topDep.getOptionalComponentsPopDep().iterator();
									while( requirementsSatisfied && subDep1Iter.hasNext() ) {
										subDep1 = subDep1Iter.next();
										if( ! relationMap.containsKey( subDep1.getRequiredLookupRelation().getPKey() ) ) {
											requirementsSatisfied = false;
										}
										else {
											subDep2Iter = subDep1.getOptionalComponentsPopDep().iterator();
											while( requirementsSatisfied && subDep2Iter.hasNext() ) {
												subDep2 = subDep2Iter.next();
												if( ! relationMap.containsKey( subDep2.getRequiredLookupRelation().getPKey() ) ) {
													requirementsSatisfied = false;
												}
												else {
													subDep3Iter = subDep2.getOptionalComponentsPopDep().iterator();
													while( requirementsSatisfied && subDep3Iter.hasNext() ) {
														subDep3 = subDep3Iter.next();
														if( ! relationMap.containsKey( subDep3.getRequiredLookupRelation().getPKey() ) ) {
															requirementsSatisfied = false;
														}
													}
												}
											}
										}
									}
								}
							}
						}
						if( requirementsSatisfied ) {
							emittedForAnyTables = true;
							relationMap.put( relation.getPKey(), relation );
							if( ! emittedAnyForTable ) {
								buff.append( "\n" );
								emitIndent();
								buff.append( "<TableAddendum FromTable=\"" + CFLibXmlUtil.formatXmlString( table.getObjName() ) + "\">\n" );
								indent();
								emittedAnyForTable = true;
							}
							emitActualRelation( relation );
						}
					}
				}
				if( emittedAnyForTable ) {
					dedent();
					emitIndent();
					buff.append( "</TableAddendum>\n" );
				}
			}
		}
	}

	protected void emitActualRelation( ICFBamRelationObj relation ) {
		final String S_ProcName = "emitActualRelation";
		emitIndent();
		ICFBamSchema.RelationTypeEnum relationType = relation.getRequiredRelationType();
		buff.append( "<" + (( relationType == ICFBamSchema.RelationTypeEnum.Superclass ) ? "SuperClass" : "" ) + "Relation Name=\"" + CFLibXmlUtil.formatXmlString( relation.getObjName() ) + "\"" );
		if( relationType != ICFBamSchema.RelationTypeEnum.Superclass ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tRelationType=\"" + relation.getRequiredRelationType().toString() + "\"" );
		}
		String shortName = relation.getOptionalShortName();
		if( ( shortName != null ) && ( shortName.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tShortName=\"" + CFLibXmlUtil.formatXmlString( shortName ) + "\"" );
		}
		String dbName = relation.getOptionalDbName();
		if( ( dbName != null ) && ( dbName.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tDbName=\"" + CFLibXmlUtil.formatXmlString( dbName ) + "\"" );
		}
		String label = relation.getOptionalLabel();
		if( ( label != null ) && ( label.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tLabel=\"" + CFLibXmlUtil.formatXmlString( label ) + "\"" );
		}
		String description = relation.getOptionalDescription();
		if( ( description != null ) && ( description.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tDescription=\"" + CFLibXmlUtil.formatXmlString( description ) + "\"" );
		}
		String shortDescription = relation.getOptionalShortDescription();
		if( ( shortDescription != null ) && ( shortDescription.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tShortDescription=\"" + CFLibXmlUtil.formatXmlString( shortDescription ) + "\"" );
		}
		String suffix = relation.getOptionalSuffix();
		if( ( suffix != null ) && ( suffix.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tSuffix=\"" + CFLibXmlUtil.formatXmlString( suffix ) + "\"" );
		}
		ICFBamIndexObj fromIndex = relation.getRequiredLookupFromIndex();
		buff.append( "\n" );
		emitIndent();
		buff.append( "\t\tFromIndex=\"" + CFLibXmlUtil.formatXmlString( fromIndex.getObjName() ) + "\"" );
		ICFBamIndexObj toIndex = relation.getRequiredLookupToIndex();
		ICFBamTableObj toTable = toIndex.getRequiredContainerTable();
		buff.append( "\n" );
		emitIndent();
		buff.append( "\t\tToIndex=\"" + CFLibXmlUtil.formatXmlString( toTable.getObjName() ) + "." + CFLibXmlUtil.formatXmlString( toIndex.getObjName() ) + "\"" );
		if( relation.getRequiredRelationType() != ICFBamSchema.RelationTypeEnum.Superclass ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tIsRequired=\"" + ( relation.getRequiredIsRequired() ? "true" : "false" ) + "\"" );
			if( relation.getRequiredIsXsdContainer() ) {
				buff.append( "\n" );
				emitIndent();
				buff.append( "\t\tIsXsdContainer=\"true\"" );
			}
			ICFBamRelationObj narrowed = relation.getOptionalLookupNarrowed();
			if( narrowed != null ) {
				ICFBamTableObj narrowedTable = narrowed.getRequiredContainerFromTable();
				buff.append( "\n" );
				emitIndent();
				buff.append( "\t\tNarrowed=\""
					+ CFLibXmlUtil.formatXmlString( narrowedTable.getObjName() )
					+ "."
					+ CFLibXmlUtil.formatXmlString( narrowed.getObjName() )
					+ "\"" );
			}
			Iterator<ICFBamPopTopDepObj> topDepIter = relation.getOptionalComponentsPopDep().iterator();
			if( topDepIter.hasNext() ) {
				ICFBamPopTopDepObj topDep = topDepIter.next();
				buff.append( "\n" );
				emitIndent();
				buff.append( "\t\tPopDepChain=\"" + CFLibXmlUtil.formatXmlString( topDep.getRequiredLookupRelation().getObjName() ) );
				Iterator<ICFBamPopSubDep1Obj> subDep1Iter = topDep.getOptionalComponentsPopDep().iterator();
				if( subDep1Iter.hasNext() ) {
					ICFBamPopSubDep1Obj subDep1 = subDep1Iter.next();
					buff.append( "." + CFLibXmlUtil.formatXmlString( subDep1.getRequiredLookupRelation().getObjName() ) );
					Iterator<ICFBamPopSubDep2Obj> subDep2Iter = subDep1.getOptionalComponentsPopDep().iterator();
					if( subDep2Iter.hasNext() ) {
						ICFBamPopSubDep2Obj subDep2 = subDep2Iter.next();
						buff.append( "." + CFLibXmlUtil.formatXmlString( subDep2.getRequiredLookupRelation().getObjName() ) );
						Iterator<ICFBamPopSubDep3Obj> subDep3Iter = subDep2.getOptionalComponentsPopDep().iterator();
						if( subDep3Iter.hasNext() ) {
							ICFBamPopSubDep3Obj subDep3 = subDep3Iter.next();
							buff.append( "." + CFLibXmlUtil.formatXmlString( subDep3.getRequiredLookupRelation().getObjName() ) );
						}
					}
				}
				buff.append( "\"" );
			}
		}
		buff.append( " >\n" );
		indent();
		ICFBamRelationColObj col;
		Iterator<ICFBamRelationColObj> colIter = relation.getOptionalComponentsColumns().iterator();
		ICFBamRelationColObj headCol = null;
		while( ( headCol == null ) && colIter.hasNext() ) {
			col = colIter.next();
			if( col.getOptionalLookupPrev() == null ) {
				headCol = col;
			}
		}
		if( headCol == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				0,
				"headCol" );
		}
		col = headCol;
		while( col != null ) {
			emitIndent();
			buff.append( "<RelationCol Name=\""
				+ CFLibXmlUtil.formatXmlString( col.getObjName() )
				+ "\" ToCol=\""
				+ CFLibXmlUtil.formatXmlString( col.getRequiredLookupToCol().getObjName() )
				+ "\" />\n" );
			col = col.getOptionalLookupNext();
		}
		dedent();
		emitIndent();
		buff.append( "</" + (( relationType == ICFBamSchema.RelationTypeEnum.Superclass ) ? "SuperClass" : "" ) + "Relation>\n" );
	}

	protected void emitTableClearDeps( ICFBamTableObj table ) {
		final String S_ProcName = "emitTableClearDeps";
		boolean emittedAny = false;
		ICFBamClearTopDepObj topDep;
		Iterator<ICFBamClearTopDepObj> iter = table.getOptionalComponentsClearDep().iterator();
		ICFBamClearTopDepObj headTopDep = null;
		if( ! iter.hasNext() ) {
			return;
		}
		while( ( headTopDep == null ) && iter.hasNext() ) {
			topDep = iter.next();
			if( topDep.getOptionalLookupPrev() == null ) {
				headTopDep = topDep;
			}
		}
		if( headTopDep == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				0,
				"headTopDep" );
		}
		topDep = headTopDep;
		while( topDep != null ) {
			if( null == topDep.getOptionalLookupDefSchema() ) {
				if( ! emittedAny ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "<TableAddendum FromTable=\"" + CFLibXmlUtil.formatXmlString( table.getObjName() ) + "\">\n" );
					indent();
					emittedAny = true;
				}
				emitIndent();
				buff.append( "<ClearDep Name=\""
					+ CFLibXmlUtil.formatXmlString( topDep.getObjName() )
					+ "\" ClearDepChain=\""
					+ CFLibXmlUtil.formatXmlString( topDep.getRequiredLookupRelation().getObjName() ) );
				Iterator<ICFBamClearSubDep1Obj> subDep1Iter = topDep.getOptionalComponentsClearDep().iterator();
				if( subDep1Iter.hasNext() ) {
					ICFBamClearSubDep1Obj subDep1 = subDep1Iter.next();
					buff.append( "." + CFLibXmlUtil.formatXmlString( subDep1.getRequiredLookupRelation().getObjName() ) );
					Iterator<ICFBamClearSubDep2Obj> subDep2Iter = subDep1.getOptionalComponentsClearDep().iterator();
					if( subDep2Iter.hasNext() ) {
						ICFBamClearSubDep2Obj subDep2 = subDep2Iter.next();
						buff.append( "." + CFLibXmlUtil.formatXmlString( subDep2.getRequiredLookupRelation().getObjName() ) );
						Iterator<ICFBamClearSubDep3Obj> subDep3Iter = subDep2.getOptionalComponentsClearDep().iterator();
						if( subDep3Iter.hasNext() ) {
							ICFBamClearSubDep3Obj subDep3 = subDep3Iter.next();
							buff.append( "." + CFLibXmlUtil.formatXmlString( subDep3.getRequiredLookupRelation().getObjName() ) );
						}
					}
				}
				buff.append( "\" />\n" );
			}
			topDep = topDep.getOptionalLookupNext();
		}
		if( emittedAny ) {
			dedent();
			emitIndent();
			buff.append( "</TableAddendum>\n" );
		}
	}

	protected void emitTableDelDeps( ICFBamTableObj table ) {
		final String S_ProcName = "emitTableDelDeps";
		boolean emittedAny = false;
		ICFBamDelTopDepObj topDep;
		Iterator<ICFBamDelTopDepObj> iter = table.getOptionalComponentsDelDep().iterator();
		ICFBamDelTopDepObj headTopDep = null;
		if( ! iter.hasNext() ) {
			return;
		}
		while( ( headTopDep == null ) && iter.hasNext() ) {
			topDep = iter.next();
			if( topDep.getOptionalLookupPrev() == null ) {
				headTopDep = topDep;
			}
		}
		if( headTopDep == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				0,
				"headTopDep" );
		}
		topDep = headTopDep;
		while( topDep != null ) {
			if( null == topDep.getOptionalLookupDefSchema() ) {
				if( ! emittedAny ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "<TableAddendum FromTable=\"" + CFLibXmlUtil.formatXmlString( table.getObjName() ) + "\">\n" );
					indent();
					emittedAny = true;
				}
				emitIndent();
				buff.append( "<DelDep Name=\""
						+ CFLibXmlUtil.formatXmlString( topDep.getObjName() )
						+ "\" DelDepChain=\""
						+ CFLibXmlUtil.formatXmlString( topDep.getRequiredLookupRelation().getObjName() ) );
					Iterator<ICFBamDelSubDep1Obj> subDep1Iter = topDep.getOptionalComponentsDelDep().iterator();
					if( subDep1Iter.hasNext() ) {
						ICFBamDelSubDep1Obj subDep1 = subDep1Iter.next();
						buff.append( "." + CFLibXmlUtil.formatXmlString( subDep1.getRequiredLookupRelation().getObjName() ) );
						Iterator<ICFBamDelSubDep2Obj> subDep2Iter = subDep1.getOptionalComponentsDelDep().iterator();
						if( subDep2Iter.hasNext() ) {
							ICFBamDelSubDep2Obj subDep2 = subDep2Iter.next();
							buff.append( "." + CFLibXmlUtil.formatXmlString( subDep2.getRequiredLookupRelation().getObjName() ) );
							Iterator<ICFBamDelSubDep3Obj> subDep3Iter = subDep2.getOptionalComponentsDelDep().iterator();
							if( subDep3Iter.hasNext() ) {
								ICFBamDelSubDep3Obj subDep3 = subDep3Iter.next();
								buff.append( "." + CFLibXmlUtil.formatXmlString( subDep3.getRequiredLookupRelation().getObjName() ) );
							}
						}
					}
					buff.append( "\" />\n" );
			}
			topDep = topDep.getOptionalLookupNext();
		}
		if( emittedAny ) {
			dedent();
			emitIndent();
			buff.append( "</TableAddendum>\n" );
		}
	}

	protected void emitTypeDef( ICFBamValueObj typeDef ) {
		ICFBamTableObj dispenserDef = null;
		if( typeDef instanceof ICFBamId16GenObj ) {
			dispenserDef = ((ICFBamId16GenObj)typeDef).getOptionalLookupDispenser();
		}
		else if( typeDef instanceof ICFBamId32GenObj ) {
			dispenserDef = ((ICFBamId32GenObj)typeDef).getOptionalLookupDispenser();
		}
		else if( typeDef instanceof ICFBamId64GenObj ) {
			dispenserDef = ((ICFBamId64GenObj)typeDef).getOptionalLookupDispenser();
		}
		if( ( dispenserDef != null ) && ( ! tableMap.containsKey( dispenserDef.getPKey() ) ) ) {
			return;
		}

		buff.append( "\n" );
		emitColumn( typeDef );
		typeMap.put( typeDef.getPKey(), typeDef );
	}

	protected void emitColumn( ICFBamValueObj value ) {
		final String S_ProcName = "emitColumn";
		emitIndent();
		String initValue;
		buff.append( "<" + value.getGenDefName() + " Name=\"" + CFLibXmlUtil.formatXmlString( value.getObjName() ) + "\"" );
		String shortName = value.getOptionalShortName();
		if( ( shortName != null ) && ( shortName.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tShortName=\"" + CFLibXmlUtil.formatXmlString( shortName ) + "\"" );
		}
		String label = value.getOptionalLabel();
		if( ( label != null ) && ( label.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tLabel=\"" + CFLibXmlUtil.formatXmlString( label ) + "\"" );
		}
		String description = value.getOptionalDescription();
		if( ( description != null ) && ( description.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tDescription=\"" + CFLibXmlUtil.formatXmlString( description ) + "\"" );
		}
		String shortDescription = value.getOptionalShortDescription();
		if( ( shortDescription != null ) && ( shortDescription.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tShortDescription=\"" + CFLibXmlUtil.formatXmlString( shortDescription ) + "\"" );
		}
		buff.append( "\n" );
		emitIndent();
		buff.append( "\t\tIsNullable=\"" + ( value.getRequiredIsNullable() ? "true" : "false" ) + "\"" );
		if( ( value.getOptionalGenerateId() != null ) && value.getOptionalGenerateId().booleanValue() ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tGenerateId=\"true\"" );
		}
		if( value instanceof ICFBamAtomObj ) {
			ICFBamAtomObj atom = (ICFBamAtomObj)value;
			String dbName = atom.getOptionalDbName();
			if( ( dbName != null ) && ( dbName.length() > 0 ) ) {
				buff.append( "\n" );
				emitIndent();
				buff.append( "\t\tDbName=\"" + CFLibXmlUtil.formatXmlString( dbName ) + "\"" );
			}
			if( atom instanceof ICFBamBlobDefObj ) {
				ICFBamBlobDefObj blobDef = (ICFBamBlobDefObj)atom;
				buff.append( "\n" );
				emitIndent();
				buff.append( "\t\tMaxLen=\"" + CFLibXmlUtil.formatInt32( blobDef.getRequiredMaxLen() ) + "\"" );
				if( null != blobDef.getOptionalInitValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tInitValue=\"" + CFLibXmlUtil.formatBlob( blobDef.getOptionalInitValue() ) + "\"" );
				}
				buff.append( " />\n" );
			}
			else if( atom instanceof ICFBamBoolDefObj ) {
				ICFBamBoolDefObj boolDef = (ICFBamBoolDefObj)atom;
				if( null != boolDef.getOptionalInitValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tInitValue=\"" + ( boolDef.getOptionalInitValue().booleanValue() ? "true" : "false" ) + "\"" );
				}
				if( null != boolDef.getOptionalFalseString() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tFalseString=\"" + CFLibXmlUtil.formatXmlString( boolDef.getOptionalFalseString() ) + "\"" );
				}
				if( null != boolDef.getOptionalTrueString() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tTrueString=\"" + CFLibXmlUtil.formatXmlString( boolDef.getOptionalTrueString() ) + "\"" );
				}
				if( null != boolDef.getOptionalNullString() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tNullString=\"" + CFLibXmlUtil.formatXmlString( boolDef.getOptionalNullString() ) + "\"" );
				}
				buff.append( " />\n" );
			}
			else if( atom instanceof ICFBamInt16DefObj ) {
				ICFBamInt16DefObj int16Def = (ICFBamInt16DefObj)atom;
				if( null != int16Def.getOptionalMinValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tMinValue=\"" + CFLibXmlUtil.formatInt16( int16Def.getOptionalMinValue() ) + "\"" );
				}
				if( null != int16Def.getOptionalMaxValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tMaxValue=\"" + CFLibXmlUtil.formatInt16( int16Def.getOptionalMaxValue() ) + "\"" );
				}
				if( null != int16Def.getOptionalInitValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tInitValue=\"" + CFLibXmlUtil.formatInt16( int16Def.getOptionalInitValue() ) + "\"" );
				}
				if( int16Def instanceof ICFBamEnumTypeObj ) {
					buff.append( " >\n" );
					indent();
					ICFBamEnumDefObj enumDef = (ICFBamEnumDefObj)atom;
					ICFBamEnumTagObj enumTag;
					ICFBamEnumTagObj headEnumTag = null;
					Iterator<ICFBamEnumTagObj> tagIter = enumDef.getRequiredComponentsTag().iterator();
					while( ( headEnumTag == null ) && tagIter.hasNext() ) {
						enumTag = tagIter.next();
						if( enumTag.getOptionalLookupPrev() == null ) {
							headEnumTag = enumTag;
						}
					}
					if( headEnumTag == null ) {
						throw new CFLibNullArgumentException( getClass(),
							S_ProcName,
							0,
							"headEnumTag" );
					}
					enumTag = headEnumTag;
					while( enumTag != null ) {
						emitIndent();
						buff.append( "<EnumTag Name=\"" + CFLibXmlUtil.formatXmlString( enumTag.getObjName() ) + "\"" );
						if( null != enumTag.getOptionalEnumCode() ) {
							buff.append( " EnumCode=\"" + CFLibXmlUtil.formatInt16( enumTag.getOptionalEnumCode() ) + "\"" );
						}
						buff.append( " />\n" );
						enumTag = enumTag.getOptionalLookupNext();
					}
					dedent();
					emitIndent();
					buff.append( "</EnumType>\n" );
				}
				else if( int16Def instanceof ICFBamId16GenObj ) {
					ICFBamId16GenObj id16Gen = (ICFBamId16GenObj)atom;
					if( null != id16Gen.getOptionalLookupDispenser() ) {
						buff.append( "\n" );
						emitIndent();
						buff.append( "\t\tDispenser=\"" + CFLibXmlUtil.formatXmlString( id16Gen.getOptionalLookupDispenser().getObjName() ) + "\"" );
					}
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tSlice=\"" + CFLibXmlUtil.formatInt16( id16Gen.getRequiredSlice() ) + "\"" );
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tBlockSize=\"" + CFLibXmlUtil.formatInt16( id16Gen.getRequiredBlockSize() ) + "\"" );
					buff.append( " />\n" );
				}
				else {
					buff.append( " />\n" );
				}
			}
			else if( atom instanceof ICFBamInt32DefObj ) {
				ICFBamInt32DefObj int32Def = (ICFBamInt32DefObj)atom;
				if( null != int32Def.getOptionalMinValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tMinValue=\"" + CFLibXmlUtil.formatInt32( int32Def.getOptionalMinValue() ) + "\"" );
				}
				if( null != int32Def.getOptionalMaxValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tMaxValue=\"" + CFLibXmlUtil.formatInt32( int32Def.getOptionalMaxValue() ) + "\"" );
				}
				if( null != int32Def.getOptionalInitValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tInitValue=\"" + CFLibXmlUtil.formatInt32( int32Def.getOptionalInitValue() ) + "\"" );
				}
				if( int32Def instanceof ICFBamId32GenObj ) {
					ICFBamId32GenObj id32Gen = (ICFBamId32GenObj)atom;
					if( null != id32Gen.getOptionalLookupDispenser() ) {
						buff.append( "\n" );
						emitIndent();
						buff.append( "\t\tDispenser=\"" + CFLibXmlUtil.formatXmlString( id32Gen.getOptionalLookupDispenser().getObjName() ) + "\"" );
					}
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tSlice=\"" + CFLibXmlUtil.formatInt16( id32Gen.getRequiredSlice() ) + "\"" );
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tBlockSize=\"" + CFLibXmlUtil.formatInt32( id32Gen.getRequiredBlockSize() ) + "\"" );
				}
				buff.append( " />\n" );
			}
			else if( atom instanceof ICFBamInt64DefObj ) {
				ICFBamInt64DefObj int64Def = (ICFBamInt64DefObj)atom;
				if( null != int64Def.getOptionalMinValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tMinValue=\"" + CFLibXmlUtil.formatInt64( int64Def.getOptionalMinValue() ) + "\"" );
				}
				if( null != int64Def.getOptionalMaxValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tMaxValue=\"" + CFLibXmlUtil.formatInt64( int64Def.getOptionalMaxValue() ) + "\"" );
				}
				if( null != int64Def.getOptionalInitValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tInitValue=\"" + CFLibXmlUtil.formatInt64( int64Def.getOptionalInitValue() ) + "\"" );
				}
				if( int64Def instanceof ICFBamId64GenObj ) {
					ICFBamId64GenObj id64Gen = (ICFBamId64GenObj)atom;
					if( null != id64Gen.getOptionalLookupDispenser() ) {
						buff.append( "\n" );
						emitIndent();
						buff.append( "\t\tDispenser=\"" + CFLibXmlUtil.formatXmlString( id64Gen.getOptionalLookupDispenser().getObjName() ) + "\"" );
					}
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tSlice=\"" + CFLibXmlUtil.formatInt16( id64Gen.getRequiredSlice() ) + "\"" );
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tBlockSize=\"" + CFLibXmlUtil.formatInt64( id64Gen.getRequiredBlockSize() ) + "\"" );
				}
				buff.append( " />\n" );
			}
			else if( atom instanceof ICFBamUInt16DefObj ) {
				ICFBamUInt16DefObj uint16Def = (ICFBamUInt16DefObj)atom;
				if( null != uint16Def.getOptionalMinValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tMinValue=\"" + CFLibXmlUtil.formatUInt16( uint16Def.getOptionalMinValue() ) + "\"" );
				}
				if( null != uint16Def.getOptionalMaxValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tMaxValue=\"" + CFLibXmlUtil.formatUInt16( uint16Def.getOptionalMaxValue() ) + "\"" );
				}
				if( null != uint16Def.getOptionalInitValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tInitValue=\"" + CFLibXmlUtil.formatUInt16( uint16Def.getOptionalInitValue() ) + "\"" );
				}
				buff.append( " />\n" );
			}
			else if( atom instanceof ICFBamUInt32DefObj ) {
				ICFBamUInt32DefObj uint32Def = (ICFBamUInt32DefObj)atom;
				if( null != uint32Def.getOptionalMinValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tMinValue=\"" + CFLibXmlUtil.formatUInt32( uint32Def.getOptionalMinValue() ) + "\"" );
				}
				if( null != uint32Def.getOptionalMaxValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tMaxValue=\"" + CFLibXmlUtil.formatUInt32( uint32Def.getOptionalMaxValue() ) + "\"" );
				}
				if( null != uint32Def.getOptionalInitValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tInitValue=\"" + CFLibXmlUtil.formatUInt32( uint32Def.getOptionalInitValue() ) + "\"" );
				}
				buff.append( " />\n" );
			}
			else if( atom instanceof ICFBamUInt64DefObj ) {
				ICFBamUInt64DefObj uint64Def = (ICFBamUInt64DefObj)atom;
				if( null != uint64Def.getOptionalMinValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tMinValue=\"" + CFLibXmlUtil.formatUInt64( uint64Def.getOptionalMinValue() ) + "\"" );
				}
				if( null != uint64Def.getOptionalMaxValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tMaxValue=\"" + CFLibXmlUtil.formatUInt64( uint64Def.getOptionalMaxValue() ) + "\"" );
				}
				if( null != uint64Def.getOptionalInitValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tInitValue=\"" + CFLibXmlUtil.formatUInt64( uint64Def.getOptionalInitValue() ) + "\"" );
				}
				buff.append( " />\n" );
			}
			else if( atom instanceof ICFBamFloatDefObj ) {
				ICFBamFloatDefObj floatDef = (ICFBamFloatDefObj)atom;
				if( null != floatDef.getOptionalMinValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tMinValue=\"" + CFLibXmlUtil.formatFloat( floatDef.getOptionalMinValue() ) + "\"" );
				}
				if( null != floatDef.getOptionalMaxValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tMaxValue=\"" + CFLibXmlUtil.formatFloat( floatDef.getOptionalMaxValue() ) + "\"" );
				}
				if( null != floatDef.getOptionalInitValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tInitValue=\"" + CFLibXmlUtil.formatFloat( floatDef.getOptionalInitValue() ) + "\"" );
				}
				buff.append( " />\n" );
			}
			else if( atom instanceof ICFBamDoubleDefObj ) {
				ICFBamDoubleDefObj doubleDef = (ICFBamDoubleDefObj)atom;
				if( null != doubleDef.getOptionalMinValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tMinValue=\"" + CFLibXmlUtil.formatDouble( doubleDef.getOptionalMinValue() ) + "\"" );
				}
				if( null != doubleDef.getOptionalMaxValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tMaxValue=\"" + CFLibXmlUtil.formatDouble( doubleDef.getOptionalMaxValue() ) + "\"" );
				}
				if( null != doubleDef.getOptionalInitValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tInitValue=\"" + CFLibXmlUtil.formatDouble( doubleDef.getOptionalInitValue() ) + "\"" );
				}
				buff.append( " />\n" );
			}
			else if( atom instanceof ICFBamNumberDefObj ) {
				ICFBamNumberDefObj numberDef = (ICFBamNumberDefObj)atom;
				buff.append( "\n" );
				emitIndent();
				buff.append( "\t\tDigits=\"" + CFLibXmlUtil.formatInt16( numberDef.getRequiredDigits() ) + "\"" );
				buff.append( "\n" );
				emitIndent();
				buff.append( "\t\tPrecis=\"" + CFLibXmlUtil.formatInt16( numberDef.getRequiredPrecis() ) + "\"" );
				if( null != numberDef.getOptionalMinValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tMinValue=\"" + CFLibXmlUtil.formatNumber( numberDef.getOptionalMinValue() ) + "\"" );
				}
				if( null != numberDef.getOptionalMaxValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tMaxValue=\"" + CFLibXmlUtil.formatNumber( numberDef.getOptionalMaxValue() ) + "\"" );
				}
				if( null != numberDef.getOptionalInitValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tInitValue=\"" + CFLibXmlUtil.formatNumber( numberDef.getOptionalInitValue() ) + "\"" );
				}
				buff.append( " />\n" );
			}
			else if( atom instanceof ICFBamDateDefObj ) {
				ICFBamDateDefObj dateDef = (ICFBamDateDefObj)atom;
				buff.append( " />\n" );
			}
			else if( atom instanceof ICFBamTimeDefObj ) {
				ICFBamTimeDefObj timeDef = (ICFBamTimeDefObj)atom;
				buff.append( " />\n" );
			}
			else if( atom instanceof ICFBamTimestampDefObj ) {
				ICFBamTimestampDefObj timestampDef = (ICFBamTimestampDefObj)atom;
				buff.append( " />\n" );
			}
			else if( atom instanceof ICFBamTZDateDefObj ) {
				ICFBamTZDateDefObj tzdateDef = (ICFBamTZDateDefObj)atom;
				buff.append( " />\n" );
			}
			else if( atom instanceof ICFBamTZTimeDefObj ) {
				ICFBamTZTimeDefObj tztimeDef = (ICFBamTZTimeDefObj)atom;
				buff.append( " />\n" );
			}
			else if( atom instanceof ICFBamTZTimestampDefObj ) {
				ICFBamTZTimestampDefObj tztimestampDef = (ICFBamTZTimestampDefObj)atom;
				buff.append( " />\n" );
			}
			else if( atom instanceof ICFBamTokenDefObj ) {
				ICFBamTokenDefObj tokenDef = (ICFBamTokenDefObj)atom;
				buff.append( "\n" );
				emitIndent();
				buff.append( "\t\tMaxLen=\"" + CFLibXmlUtil.formatInt32( tokenDef.getRequiredMaxLen() ) + "\"" );
				initValue = tokenDef.getOptionalInitValue();
				if( ( ! tokenDef.getRequiredIsNullable() ) && ( initValue == null ) ) {
					initValue = "";
				}
				if( null != initValue ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tInitValue=\"" + CFLibXmlUtil.formatXmlString( initValue ) + "\"" );
				}
				buff.append( " />\n" );
			}
			else if( atom instanceof ICFBamNmTokenDefObj ) {
				ICFBamNmTokenDefObj nmtokenDef = (ICFBamNmTokenDefObj)atom;
				buff.append( "\n" );
				emitIndent();
				buff.append( "\t\tMaxLen=\"" + CFLibXmlUtil.formatInt32( nmtokenDef.getRequiredMaxLen() ) + "\"" );
				initValue = nmtokenDef.getOptionalInitValue();
				if( ( ! nmtokenDef.getRequiredIsNullable() ) && ( initValue == null ) ) {
					initValue = "";
				}
				if( null != initValue ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tInitValue=\"" + CFLibXmlUtil.formatXmlString( initValue ) + "\"" );
				}
				buff.append( " />\n" );
			}
			else if( atom instanceof ICFBamNmTokensDefObj ) {
				ICFBamNmTokensDefObj nmtokensDef = (ICFBamNmTokensDefObj)atom;
				buff.append( "\n" );
				emitIndent();
				buff.append( "\t\tMaxLen=\"" + CFLibXmlUtil.formatInt32( nmtokensDef.getRequiredMaxLen() ) + "\"" );
				initValue = nmtokensDef.getOptionalInitValue();
				if( ( ! nmtokensDef.getRequiredIsNullable() ) && ( initValue == null ) ) {
					initValue = "";
				}
				if( null != initValue ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tInitValue=\"" + CFLibXmlUtil.formatXmlString( initValue ) + "\"" );
				}
				buff.append( " />\n" );
			}
			else if( atom instanceof ICFBamTextDefObj ) {
				ICFBamTextDefObj textDef = (ICFBamTextDefObj)atom;
				buff.append( "\n" );
				emitIndent();
				buff.append( "\t\tMaxLen=\"" + CFLibXmlUtil.formatInt32( textDef.getRequiredMaxLen() ) + "\"" );
				initValue = textDef.getOptionalInitValue();
				if( ( ! textDef.getRequiredIsNullable() ) && ( initValue == null ) ) {
					initValue = "";
				}
				if( null != initValue ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tInitValue=\"" + CFLibXmlUtil.formatXmlString( initValue ) + "\"" );
				}
				buff.append( " />\n" );
			}
			else if( atom instanceof ICFBamStringDefObj ) {
				ICFBamStringDefObj stringDef = (ICFBamStringDefObj)atom;
				buff.append( "\n" );
				emitIndent();
				buff.append( "\t\tMaxLen=\"" + CFLibXmlUtil.formatInt32( stringDef.getRequiredMaxLen() ) + "\"" );
				initValue = stringDef.getOptionalInitValue();
				if( ( ! stringDef.getRequiredIsNullable() ) && ( initValue == null ) ) {
					initValue = "";
				}
				if( null != initValue ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tInitValue=\"" + CFLibXmlUtil.formatXmlString( initValue ) + "\"" );
				}
				buff.append( " />\n" );
			}
			else if( atom instanceof ICFBamUuidDefObj ) {
				ICFBamUuidDefObj uuidDef = (ICFBamUuidDefObj)atom;
				if( null != uuidDef.getOptionalInitValue() ) {
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tInitValue=\"" + CFLibXmlUtil.formatUuid( uuidDef.getOptionalInitValue() ) + "\"" );
				}
				if( uuidDef instanceof ICFBamUuidGenObj ) {
					ICFBamUuidGenObj uuidGen = (ICFBamUuidGenObj)atom;
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tSlice=\"" + CFLibXmlUtil.formatInt16( uuidGen.getRequiredSlice() ) + "\"" );
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tBlockSize=\"" + CFLibXmlUtil.formatInt32( uuidGen.getRequiredBlockSize() ) + "\"" );
				}
				buff.append( " />\n" );
			}
			else {
				throw new CFLibUnsupportedClassException( getClass(),
					S_ProcName,
					"atom",
					atom,
					"The usual culprits" );
			}
		}
		else if( value instanceof ICFBamTableColObj ) {
			ICFBamTableColObj tableCol = (ICFBamTableColObj)value;
			String dbName = tableCol.getOptionalDbName();
			if( ( dbName != null ) && ( dbName.length() > 0 ) ) {
				buff.append( "\n" );
				emitIndent();
				buff.append( "\t\tDbName=\"" + CFLibXmlUtil.formatXmlString( dbName ) + "\"" );
			}
			ICFBamValueObj dataType = tableCol.getRequiredParentDataType();
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tDataType=\"" + CFLibXmlUtil.formatXmlString( dataType.getObjName() ) + "\"" );
			buff.append( " />\n" );
		}
	}

	protected void emitIndex( ICFBamIndexObj index, boolean isPrimary ) {
		final String S_ProcName = "emitIndex";
		emitIndent();
		buff.append( "<" + ( isPrimary ? "Primary" : "" ) + "Index Name=\""	+ CFLibXmlUtil.formatXmlString( index.getObjName() ) + "\"\n" );
		emitIndent();
		buff.append( "\t\tIsDbMapped=\"" + ( index.getRequiredIsDbMapped() ? "true" : "false" ) + "\"\n" );
		emitIndent();
		buff.append( "\t\tIsUnique=\"" + ( index.getRequiredIsUnique() ? "true" : "false" ) + "\"" );
		String dbName = index.getOptionalDbName();
		if( ( dbName != null ) && ( dbName.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tDbName=\"" + CFLibXmlUtil.formatXmlString( dbName ) + "\"" );
		}
		String shortName = index.getOptionalShortName();
		if( ( shortName != null ) && ( shortName.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tShortName=\"" + CFLibXmlUtil.formatXmlString( shortName ) + "\"" );
		}
		String suffix = index.getOptionalSuffix();
		if( ( suffix != null ) && ( suffix.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tSuffix=\"" + CFLibXmlUtil.formatXmlString( suffix ) + "\"" );
		}
		String label = index.getOptionalLabel();
		if( ( label != null ) && ( label.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tLabel=\"" + CFLibXmlUtil.formatXmlString( label ) + "\"" );
		}
		String description = index.getOptionalDescription();
		if( ( description != null ) && ( description.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tDescription=\"" + CFLibXmlUtil.formatXmlString( description ) + "\"" );
		}
		String shortDescription = index.getOptionalShortDescription();
		if( ( shortDescription != null ) && ( shortDescription.length() > 0 ) ) {
			buff.append( "\n" );
			emitIndent();
			buff.append( "\t\tShortDescription=\"" + CFLibXmlUtil.formatXmlString( shortDescription ) + "\"" );
		}
		buff.append( " >\n" );
		indent();
		ICFBamIndexColObj col;
		Iterator<ICFBamIndexColObj> iter = index.getOptionalComponentsColumns().iterator();
		ICFBamIndexColObj headCol = null;
		while( ( headCol == null ) && iter.hasNext() ) {
			col = iter.next();
			if( col.getOptionalLookupPrev() == null ) {
				headCol = col;
			}
		}
		if( headCol == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				0,
				"headCol" );
		}
		col = headCol;
		while( col != null ) {
			emitIndent();
			buff.append( "<IndexCol Name=\""
				+ CFLibXmlUtil.formatXmlString( col.getObjName() )
				+ "\" IsAscending=\""
				+ ( col.getRequiredIsAscending() ? "true" : "false" )
				+ "\" />\n" );
			col = col.getOptionalLookupNext();
		}
		dedent();
		emitIndent();
		buff.append( "</" + ( isPrimary ? "Primary" : "" ) + "Index>\n" );
	}

	public void emitServerMethods( ICFBamSchemaDefObj schemaDef ) {
		boolean emittedAny;
		ICFBamTableObj table;
		ICFBamServerMethodObj method;
		Iterator<ICFBamServerMethodObj> methodIter;
		Iterator<ICFBamTableObj> tableIter = alphabeticalTableList.iterator();
		while( tableIter.hasNext() ) {
			table = tableIter.next();
			emittedAny = false;
			methodIter = table.getOptionalComponentsServerMethods().iterator();
			while( methodIter.hasNext() ) {
				method = methodIter.next();
				if( null == method.getOptionalLookupDefSchema() ) {
					if( ! emittedAny ) {
						buff.append( "\n" );
						emitIndent();
						buff.append( "<TableAddendum FromTable=\"" + CFLibXmlUtil.formatXmlString( table.getObjName() ) + "\">\n" );
						indent();
						emittedAny = true;
					}
					emitIndent();
					buff.append( "<" + method.getGenDefName() + " Name=\"" + CFLibXmlUtil.formatXmlString( method.getObjName() ) + "\"" );
					String shortName = method.getOptionalShortName();
					if( ( shortName != null ) && ( shortName.length() > 0 ) ) {
						buff.append( "\n" );
						emitIndent();
						buff.append( "\t\tShortName=\"" + CFLibXmlUtil.formatXmlString( shortName ) + "\"" );
					}
					String label = method.getOptionalLabel();
					if( ( label != null ) && ( label.length() > 0 ) ) {
						buff.append( "\n" );
						emitIndent();
						buff.append( "\t\tLabel=\"" + CFLibXmlUtil.formatXmlString( label ) + "\"" );
					}
					String description = method.getOptionalDescription();
					if( ( description != null ) && ( description.length() > 0 ) ) {
						buff.append( "\n" );
						emitIndent();
						buff.append( "\t\tDescription=\"" + CFLibXmlUtil.formatXmlString( description ) + "\"" );
					}
					String shortDescription = method.getOptionalShortDescription();
					if( ( shortDescription != null ) && ( shortDescription.length() > 0 ) ) {
						buff.append( "\n" );
						emitIndent();
						buff.append( "\t\tShortDescription=\"" + CFLibXmlUtil.formatXmlString( shortDescription ) + "\"" );
					}
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tIsInstanceMethod=\"" + ( method.getRequiredIsInstanceMethod() ? "true" : "false" ) + "\"" );
					if( method instanceof ICFBamServerObjFuncObj ) {
						ICFBamServerObjFuncObj objFunc = (ICFBamServerObjFuncObj)method;
						ICFBamTableObj retTable = objFunc.getOptionalLookupRetTable();
						if( retTable != null ) {
							buff.append( "\n" );
							emitIndent();
							buff.append( "\t\tReturnTable=\"" + CFLibXmlUtil.formatXmlString( retTable.getObjName() ) + "\"" );
						}
					}
					else if( method instanceof ICFBamServerListFuncObj ) {
						ICFBamServerListFuncObj listFunc = (ICFBamServerListFuncObj)method;
						ICFBamTableObj retTable = listFunc.getOptionalLookupRetTable();
						if( retTable != null ) {
							buff.append( "\n" );
							emitIndent();
							buff.append( "\t\tReturnTable=\"" + CFLibXmlUtil.formatXmlString( retTable.getObjName() ) + "\"" );
						}
					}
					buff.append( " >" );
					indent();

					ICFBamParamObj param;
					Iterator<ICFBamParamObj> paramIter = method.getOptionalComponentsParams().iterator();
					while( paramIter.hasNext() ) {
						param = paramIter.next();
						buff.append( "\n" );
						emitIndent();
						buff.append( "<Param Name=\"" + CFLibXmlUtil.formatXmlString( param.getObjName() ) + "\"" );
						description = param.getOptionalDescription();
						if( ( description != null ) && ( description.length() > 0 ) ) {
							buff.append( "\n" );
							emitIndent();
							buff.append( "\t\tDescription=\"" + CFLibXmlUtil.formatXmlString( description ) + "\"" );
						}
						shortDescription = param.getOptionalShortDescription();
						if( ( shortDescription != null ) && ( shortDescription.length() > 0 ) ) {
							buff.append( "\n" );
							emitIndent();
							buff.append( "\t\tShortDescription=\"" + CFLibXmlUtil.formatXmlString( shortDescription ) + "\"" );
						}
						buff.append( "\n" );
						emitIndent();
						buff.append( "\t\tIsNullable=\"" + ( param.getRequiredIsNullable() ? "true" : "false" ) + "\"" );
						ICFBamValueObj typeDef = param.getRequiredLookupType();
						buff.append( "\n" );
						emitIndent();
						buff.append( "\t\tType=\"" + CFLibXmlUtil.formatXmlString( typeDef.getObjName() ) + "\" />" );
					}

					buff.append( "\n" );
					emitIndent();
					buff.append( "<CafeMethodBody>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( method.getRequiredJMethodBody() ) + "</CafeMethodBody>\n" );

					buff.append( "\n" );
					emitIndent();
					buff.append( "<CPlusMethodBody>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( method.getRequiredCppMethodBody() ) + "</CPlusMethodBody>\n" );

					buff.append( "\n" );
					emitIndent();
					buff.append( "<CSharpMethodBody>" + CFLibXmlUtil.formatXmlStringWhitespacePreserve( method.getRequiredCsMethodBody() ) + "</CSharpMethodBody>\n" );

					dedent();
					emitIndent();
					buff.append( "</" + method.getGenDefName() + ">\n" );
				}
			}
			if( emittedAny ) {
				dedent();
				emitIndent();
				buff.append( "</TableAddendum>\n" );
			}
		}
	}

	protected void emitChains( ICFBamSchemaDefObj schemaDef ) {
		ICFBamChainObj chain;
		ICFBamTableObj table;
		boolean emittedAny;
		Iterator<ICFBamTableObj> iterTable = alphabeticalTableList.iterator();
		while( iterTable.hasNext() ) {
			table = iterTable.next();
			emittedAny = false;
			Iterator<ICFBamChainObj> iterChain = table.getOptionalComponentsChains().iterator();
			while( iterChain.hasNext() ) {
				chain = iterChain.next();
				if( null == chain.getOptionalLookupDefSchema() ) {
					if( ! emittedAny ) {
						buff.append( "\n" );
						emitIndent();
						buff.append( "<TableAddendum FromTable=\"" + CFLibXmlUtil.formatXmlString( table.getObjName() ) + "\">\n" );
						indent();
						emittedAny = true;
					}
					emitIndent();
					buff.append( "<Chain Name=\"" + CFLibXmlUtil.formatXmlString( chain.getObjName() ) + "\"" );
					String shortName = chain.getOptionalShortName();
					if( ( shortName != null ) && ( shortName.length() > 0 ) ) {
						buff.append( "\n" );
						emitIndent();
						buff.append( "\t\tShortName=\"" + CFLibXmlUtil.formatXmlString( shortName ) + "\"" );
					}
					String label = chain.getOptionalLabel();
					if( ( label != null ) && ( label.length() > 0 ) ) {
						buff.append( "\n" );
						emitIndent();
						buff.append( "\t\tLabel=\"" + CFLibXmlUtil.formatXmlString( label ) + "\"" );
					}
					String description = chain.getOptionalDescription();
					if( ( description != null ) && ( description.length() > 0 ) ) {
						buff.append( "\n" );
						emitIndent();
						buff.append( "\t\tDescription=\"" + CFLibXmlUtil.formatXmlString( description ) + "\"" );
					}
					String shortDescription = chain.getOptionalShortDescription();
					if( ( shortDescription != null ) && ( shortDescription.length() > 0 ) ) {
						buff.append( "\n" );
						emitIndent();
						buff.append( "\t\tShortDescription=\"" + CFLibXmlUtil.formatXmlString( shortDescription ) + "\"" );
					}
					String suffix = chain.getOptionalSuffix();
					if( ( suffix != null ) && ( suffix.length() > 0 ) ) {
						buff.append( "\n" );
						emitIndent();
						buff.append( "\t\tSuffix=\"" + CFLibXmlUtil.formatXmlString( suffix ) + "\"" );
					}
					ICFBamRelationObj prevRelation = chain.getRequiredLookupPrevRel();
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tPrevRel=\"" + CFLibXmlUtil.formatXmlString( prevRelation.getObjName() ) + "\"" );
					ICFBamRelationObj nextRelation = chain.getRequiredLookupNextRel();
					buff.append( "\n" );
					emitIndent();
					buff.append( "\t\tNextRel=\"" + CFLibXmlUtil.formatXmlString( nextRelation.getObjName() ) + "\" />\n" );
				}
			}
			if( emittedAny ) {
				dedent();
				emitIndent();
				buff.append( "</TableAddendum>\n" );
			}
		}
	}
}
