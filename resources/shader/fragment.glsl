#version 110

uniform sampler2D texture_diffuse;

varying vec4 pass_Color;
varying vec2 pass_TextureCoord;


void main(void) {

		gl_FragColor = pass_Color * texture2D(texture_diffuse, pass_TextureCoord);


}