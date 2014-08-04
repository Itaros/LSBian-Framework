package ru.itaros.lsbrl.structure;

public enum AttributeContentType {
//	TYPE NAME					TYPE ID		SIZE(bytes)
	NULL						(0x00	,		0		),
	UINT8						(0x01	,		1		),
	INT16						(0x02	,		2		),	
	UINT16						(0x03	,		2		),	
	INT32						(0x04	,		4		),	
	UINT32						(0x05	,		4		),	
	FLOAT						(0x06	,		4		),
	DOUBLE						(0x07	,		8		),
	INTVEC2						(0x08	,		8		),
	INTVEC3						(0x09	,		12		),
	INTVEC4						(0x0A	,		16		),	
	VEC2						(0x0B	,		8		),	
	VEC3						(0x0C	,		12		),	
	VEC4						(0x0D	,		16		),	
	MATRIX2x2					(0x0E	,		16		),	
	MATRIX3x3					(0x0F	,		36		),	
	MATRIX3x4					(0x10	,		48		),
	MATRIX4x3					(0x11	,		48		),
	MATRIX4x4					(0x12	,		64		),	
	BOOL						(0x13	,		1		),	
	STRING_UTF_UINT32_1			(0x14					),	
	STRING_UTF_UINT32_2			(0x15					),	
	STRING_UTF_UINT32_3			(0x16					),
	STRING_UTF_UINT32_PADDING_1	(0x17					),
	STRING_UTF_UINT32_PADDING_2	(0x18					),
	STREAM						(0x19					),
	UINT64						(0x1A	,		8		),	
	INT8						(0x1B	,		1		),	
	STRING_LOCALIZED			(0x1C					),	
	STRING_UTF16				(0x1D					),	
	STRING_UTF16_PADDING		(0x1E					);	
	
	private int typeid;
	private int size=-1;
	
	//No predefined size
	private AttributeContentType(int typeid){
		this.typeid=typeid;
	}
	private AttributeContentType(int typeid, int size){
		this(typeid);
		this.size=size;
	}	
	
	public int getID(){
		return typeid;
	}
}
