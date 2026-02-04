// Description: Java 25 Custom JavaFX in-memory CFBam editor

/*
 *	io.github.msobkow.CFBam
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

package io.github.msobkow.v3_1.cfbam.cfbamcusteditor;

import java.io.Serializable;
import java.math.*;
import java.text.*;
import java.time.*;
import java.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.text.StringEscapeUtils;
import io.github.msobkow.v3_1.cflib.*;
import io.github.msobkow.v3_1.cflib.dbutil.*;
import io.github.msobkow.v3_1.cflib.javafx.*;
import io.github.msobkow.v3_1.cflib.javafx.CFReferenceEditor.ICFReferenceCallback;
import io.github.msobkow.v3_1.cflib.xml.CFLibXmlUtil;
import io.github.msobkow.v3_1.cfcore.msscf.*;
import io.github.msobkow.v3_1.cfsec.cfsec.*;
import io.github.msobkow.v3_1.cfint.cfint.*;
import io.github.msobkow.v3_1.cfbam.cfbam.*;
import io.github.msobkow.v3_1.cfsec.cfsecobj.*;
import io.github.msobkow.v3_1.cfint.cfintobj.*;
import io.github.msobkow.v3_1.cfbam.cfbamobj.*;
import io.github.msobkow.v3_1.cfsec.cfsecsaxloader.*;
import io.github.msobkow.v3_1.cfint.cfintsaxloader.*;
import io.github.msobkow.v3_1.cfbam.cfbamsaxloader.*;
import io.github.msobkow.v3_1.cfsec.cfsecjavafx.*;
import io.github.msobkow.v3_1.cfint.cfintjavafx.*;
import io.github.msobkow.v3_1.cfbam.cfbamjavafx.*;
import io.github.msobkow.v3_1.cfbam.cfbamram.*;
import io.github.msobkow.v3_1.cfbam.cfbammsscf.*;
import io.github.msobkow.v3_1.cfbam.cfbamcustmssbamcf.*;
import io.github.msobkow.v3_1.cfbam.cfbamcustxmlloader.*;
import io.github.msobkow.v3_1.cfsec.cfseccustfx.*;
import io.github.msobkow.v3_1.cfint.cfintcustfx.*;
import io.github.msobkow.v3_1.cfbam.cfbamcustfx.*;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CFBamCustEditor
extends Application
{
	private static ICFLibMessageLog log = new CFLibConsoleMessageLog();
	protected static ICFBamSchemaObj schema = null;
	protected static ICFBamCustEditorSchema customSchema = null;
	protected static String modelName = "";
	protected static CFBamRamSchema ramSchema = null;

	// Constructors

	public CFBamCustEditor() {
	}

	@Override public void start( Stage stage ) {

		//CFBamCustMainWindow mainWindow = new CFBamCustMainWindow( customSchema );

		//mainWindow.requestFocus();

		CFBamCustEditorMainPane mainPane = new CFBamCustEditorMainPane( customSchema, modelName );
		mainPane.setCustomSchema( customSchema );
		Scene scene = new Scene( mainPane );

		stage.setTitle( "MSS Code Factory JavaFX BAM Editor" );
		stage.setMinHeight( 640.0 );
		stage.setMinWidth( 1000.0 );
		stage.setScene( scene );
		stage.sizeToScene();
		stage.show();
		stage.requestFocus();
	}

	// Bamessors

	public static ICFBamCustSchema getCustomSchema() {
		return( customSchema );
	}

	public static void setCustomSchema( ICFBamCustEditorSchema argSchema ) {
		customSchema = argSchema;
	}

	// CFBamRamSchema accessors are needed to complete the wiring of the direct request invoker instance
	// that has been bound by the main() to a PostgreSQL persistance implementation.

	public static CFBamRamSchema getRamSchema() {
		return( ramSchema );
	}

	public static void setRamSchema( CFBamRamSchema invoker ) {
		ramSchema = invoker;
	}

	public static ICFBamSchemaObj getSchema() {
		return( schema );
	}

	public static void setSchema( ICFBamSchemaObj argSchema ) {
		schema = argSchema;
	}

	// main() entry point

	public static void main( String args[] ) {
		final String S_ProcName = "CFBamCustEditor.main() ";
		initConsoleLog();

		if( args.length >= 1 ) {
			modelName = args[0];
		}

		if( args.length >= 2 ) {
			if( args[1].equals( "trace" ) ) {
				CFConsole.setLogExceptionsToSystem( true );
			}
			else if( args[1].equals( "notrace" ) ) {
				CFConsole.setLogExceptionsToSystem( false );
			}
			else {
				log.message( S_ProcName + "ERROR: Second argument if specified must be either \"trace\" or \"notrace\"" );
				return;
			}
		}
		else {
			CFConsole.setLogExceptionsToSystem( false );
		}

		if( args.length > 2 ) {
			log.message( S_ProcName + "ERROR: A maximum of two arguments may be specified -- the model name to load and either trace or notrace" );
			return;
		}

		String homeDirName = System.getProperty( "HOME" );
		if( homeDirName == null ) {
			homeDirName = System.getProperty( "user.home" );
			if( homeDirName == null ) {
				log.message( S_ProcName + "ERROR: Home directory not set" );
				return;
			}
		}
		File homeDir = new File( homeDirName );
		if( ! homeDir.exists() ) {
			log.message( S_ProcName + "ERROR: Home directory \"" + homeDirName + "\" does not exist" );
			return;
		}
		if( ! homeDir.isDirectory() ) {
			log.message( S_ProcName + "ERROR: Home directory \"" + homeDirName + "\" is not a directory" );
			return;
		}

		// Configure logging
		Properties sysProps = System.getProperties();
		sysProps.setProperty( "log4j.rootCategory", "WARN" );
		sysProps.setProperty( "org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Log4JLogger" );

		Logger httpLogger = Logger.getLogger( CFBamCustEditor.class );
		httpLogger.setLevel( Level.WARN );

		// The Invoker and it's implementation
		CFBamRamSchema invoker = new CFBamRamSchema();

		// And now for the client side cache implementation that invokes it
		ICFBamSchemaObj clientSchemaObj = new CFBamSchemaObj() {
			public void logout() {
			}
		};
		clientSchemaObj.setBackingStore( invoker );

		ICFSecClusterObj cluster = clientSchemaObj.getClusterTableObj().newInstance();
		ICFSecClusterEditObj editCluster = cluster.beginEdit();
		editCluster.setRequiredFullDomName( "system" );
		editCluster.setRequiredDescription( "system" );
		cluster = editCluster.create();
		editCluster = null;

		ICFSecTenantObj tenant = clientSchemaObj.getTenantTableObj().newInstance();
		ICFSecTenantEditObj editTenant = tenant.beginEdit();
		editTenant.setRequiredContainerCluster( cluster );
		editTenant.setRequiredTenantName( "system" );
		tenant = editTenant.create();
		editTenant = null;

		ICFSecSecUserObj sysuser = clientSchemaObj.getSecUserTableObj().newInstance();
		ICFSecSecUserEditObj editSysuser = sysuser.beginEdit();
		editSysuser.setRequiredEMailAddress( "system" );
		editSysuser.setRequiredLoginId( "system" );
		editSysuser.setRequiredPasswordHash( "" );
		sysuser = editSysuser.create();
		editSysuser = null;

		ICFSecSecSessionObj session = clientSchemaObj.getSecSessionTableObj().newInstance();
		ICFSecSecSessionEditObj editSession = session.beginEdit();
		editSession.setRequiredContainerSecUser( sysuser );
		editSession.setRequiredStart( Calendar.getInstance() );
		session = editSession.create();
		editSession = null;

		CFSecAuthorization authorization = new CFSecAuthorization();
		authorization.setSecCluster( cluster );
		authorization.setSecTenant( tenant );
		authorization.setSecSession( session );
		clientSchemaObj.setAuthorization( authorization );

		// And now we can stitch together the CLI to the SAX loader code
		CFBamCustEditor cli = new CFBamCustEditor();
		cli.setRamSchema( invoker );
		cli.setSchema( clientSchemaObj );

		ICFBamCustEditorSchema myCustomSchema = new CFBamCustEditorSchema();
		myCustomSchema.setSchema( clientSchemaObj );
		myCustomSchema.setClusterName( "system" );
		myCustomSchema.setTenantName( "system" );
		myCustomSchema.setSecUserName( "system" );
		cli.setCustomSchema( myCustomSchema );

		Application.launch( args );
	}

	// Initialize the console log

	protected static void initConsoleLog() {
//		Layout layout = new PatternLayout(
//				"%d{ISO8601}"		// Start with a timestamp
//			+	" %-5p"				// Include the severity
//			+	" %C.%M"			// pkg.class.method()
//			+	" %F[%L]"			// File[lineNumber]
//			+	": %m\n" );			// Message text
//		BasicConfigurator.configure( new ConsoleAppender( layout, "System.out" ) );
	}

}
