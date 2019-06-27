package programma;

public class Metadata {
	
		////////////////////////////////////////////////////////////attributes
	
		String alias;
		String source;
		String type;
		
		
		///////////////////////////////////////////////////////////constructor
		
		public Metadata(String alias,String source,String type) {
			this.alias=alias;
			this.source=source;
			this.type=type;
		}


		
		///////////////////////////////////////////////////////////getters and setters
		
		public String getAlias() {
			return alias;
		}


		public void setAlias(String alias) {
			this.alias = alias;
		}


		public String getSource() {
			return source;
		}


		public void setSource(String source) {
			this.source = source;
		}


		public String getType() {
			return type;
		}


		public void setType(String type) {
			this.type = type;
		}
		
}
