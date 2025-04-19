package org.probato.test.script;

import org.probato.api.Script;
import org.probato.entity.type.Complexity;
import org.probato.entity.type.Flow;
import org.probato.entity.type.Relevance;

@Script(
	code = "UC01TC07", 
	name = "UC01TC07", 
	description = UC07TC01_ScriptDescriptionMaxLength.DESCRIPTION, 
	flow = Flow.ALTERNATIVE, 
	complexity = Complexity.AVERAGE, 
	relevance = Relevance.AVERAGE)
public class UC07TC01_ScriptDescriptionMaxLength {

	public static final String DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec accumsan massa tellus. Nulla sed tortor at magna feugiat feugiat. Donec eget nisi mi. Cras dapibus ligula ligula, id dignissim massa laoreet quis. Donec eu facilisis eros. Cras nulla lacus, commodo ultrices porttitor a, sodales nec lacus. Integer sollicitudin sem sit amet rutrum imperdiet. Proin a risus sed sapien faucibus porta. Fusce diam dolor, fringilla eu urna sed, scelerisque imperdiet lorem. Nullam posuere consectetur massa id finibus. Donec at ipsum elementum, ultricies est at, gravida nisl. Nullam sit amet orci ut purus maximus auctor ac nec ex. Quisque sollicitudin purus iaculis tincidunt cursus. Nam vestibulum, risus sollicitudin commodo accumsan, diam lacus congue enim, sit amet suscipit risus ex at leo.\r\n"
			+ "\r\n"
			+ "Etiam blandit orci sit amet tellus eleifend tempor at id tellus. Donec fringilla leo lectus, ut congue mauris porta eu. Fusce aliquet ut massa quis condimentum. Aenean eleifend sodales nulla, eu interdum neque porta non. Curabitur sit amet blandit leo. Nam venenatis gravida mauris in elementum. Nam venenatis cursus congue. Vivamus nisl tellus, tincidunt sed dictum et, condimentum ut magna.\r\n"
			+ "\r\n"
			+ "Pellentesque est felis, molestie non nibh quis, rhoncus auctor quam. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Mauris at pharetra ligula. Nunc quis dictum ex. Nunc dignissim purus sem, sed malesuada nulla rutrum ut. Fusce ac tellus vel risus dictum porttitor non ut mi. Proin mollis ultrices risus vel faucibus. Fusce rutrum aliquet risus non eleifend. Mauris tincidunt nec massa sed mattis.\r\n"
			+ "\r\n"
			+ "Pellentesque suscipit eleifend metus, tincidunt luctus purus congue eget. Donec commodo orci ipsum, id malesuada ex posuere eget. Aliquam gravida risus sit amet hendrerit dapibus. Aliquam suscipit tellus nec nibh sagittis, id tristique arcu sodales. Duis ipsum purus, sagittis non elit id, scelerisque semper velit. Pellentesque lobortis sapien non lorem aliquet, vel feugiat mi.";

}