// Description: Java 25 JavaFX Display Element Factory for TZDateCol.

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

/**
 *	CFBamJavaFXTZDateColFactory JavaFX Display Element Factory
 *	for TZDateCol.
 */
public class CFBamCustEditorTZDateColFactory
extends CFBamJavaFXTZDateColFactory
{
	public CFBamCustEditorTZDateColFactory( ICFBamJavaFXSchema argSchema ) {
		super( argSchema );
	}

	public CFGridPane newAttrPane( ICFFormManager formManager, ICFBamTZDateColObj argFocus ) {
		CFBamCustEditorTZDateColAttrPane retnew = new CFBamCustEditorTZDateColAttrPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newListPane( ICFFormManager formManager,
		ICFBamTableObj argContainer,
		ICFBamTZDateColObj argFocus,
		Collection<ICFBamTZDateColObj> argDataCollection,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
		CFBamJavaFXTZDateColListPane retnew = new CFBamJavaFXTZDateColListPane( formManager,
			javafxSchema,
			argContainer,
			argFocus,
			argDataCollection,
			refreshCallback,
			sortByChain );
		return( retnew );
	}

	public CFBorderPane newPickerPane( ICFFormManager formManager,
		ICFBamTZDateColObj argFocus,
		ICFBamTableObj argContainer,
		Collection<ICFBamTZDateColObj> dataCollection,
		ICFBamJavaFXTZDateColChosen whenChosen )
	{
		CFBamJavaFXTZDateColPickerPane retnew = new CFBamJavaFXTZDateColPickerPane( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			dataCollection,
			whenChosen );
		return( retnew );
	}

	public CFTabPane newEltTabPane( ICFFormManager formManager, ICFBamTZDateColObj argFocus ) {
		CFBamJavaFXTZDateColEltTabPane retnew = new CFBamJavaFXTZDateColEltTabPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newAddPane( ICFFormManager formManager, ICFBamTZDateColObj argFocus ) {
		CFBamJavaFXTZDateColAddPane retnew = new CFBamJavaFXTZDateColAddPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newViewEditPane( ICFFormManager formManager, ICFBamTZDateColObj argFocus ) {
		CFBamJavaFXTZDateColViewEditPane retnew = new CFBamJavaFXTZDateColViewEditPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newAskDeleteForm( ICFFormManager formManager, ICFBamTZDateColObj argFocus, ICFDeleteCallback callback ) {
		CFBamJavaFXTZDateColAskDeleteForm retnew = new CFBamJavaFXTZDateColAskDeleteForm( formManager, javafxSchema, argFocus, callback );
		return( retnew );
	}

	public CFBorderPane newPickerForm( ICFFormManager formManager,
		ICFBamTZDateColObj argFocus,
		ICFBamTableObj argContainer,
		Collection<ICFBamTZDateColObj> argDataCollection,
		ICFBamJavaFXTZDateColChosen whenChosen )
	{
		CFBamJavaFXTZDateColPickerForm retnew = new CFBamJavaFXTZDateColPickerForm( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFBorderPane newAddForm( ICFFormManager formManager, ICFBamTZDateColObj argFocus, ICFFormClosedCallback closeCallback, boolean allowSave ) {
		CFBamJavaFXTZDateColAddForm retnew = new CFBamJavaFXTZDateColAddForm( formManager, javafxSchema, argFocus, closeCallback, allowSave );
		return( retnew );
	}

	public CFBorderPane newViewEditForm( ICFFormManager formManager, ICFBamTZDateColObj argFocus, ICFFormClosedCallback closeCallback, boolean cameFromAdd ) {
		CFBamCustEditorTZDateColViewEditForm retnew = new CFBamCustEditorTZDateColViewEditForm( formManager, javafxSchema, argFocus, closeCallback, cameFromAdd );
		return( retnew );
	}
}
