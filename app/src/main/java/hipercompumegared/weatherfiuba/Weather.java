/**
 * This is a tutorial source code 
 * provided "as is" and without warranties.
 *
 * For any question please visit the web site
 * http://www.survivingwithandroid.com
 *
 * or write an email to
 * survivingwithandroid@gmail.com
 *
 */
package hipercompumegared.weatherfiuba;

import java.util.Calendar;

/*
 * Copyright (C) 2013 Surviving with Android (http://www.survivingwithandroid.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class Weather {

	private enum Hora{
		DIA,NOCHE;
		public   static Hora devolverHora(int hora){
			if((hora>9)&&(hora<18)){
				return DIA;
			}
			return NOCHE;
		}
	}

	private enum Clima{
		SOLEADA("Clear"),NUBLADO("nublado"),LLUVIA("lluvia");
		private  String clima;
		private Clima(String clima){
			this.clima=clima;
		}
		public static Clima devolverClime(String clima){
			if(clima==SOLEADA.clima){
				return  SOLEADA;
			}
			if(clima==NUBLADO.clima){
				return  SOLEADA;
			}
			return  LLUVIA;
		}
		public String ToString(){
			return clima;
		}
	}

	private enum  TemperaturaStatus{
		PLAYA,CIUDAD,MONTANIA;
		public static  TemperaturaStatus devolverTemperatura(float temperatura){
			if(temperatura>20){
				return TemperaturaStatus.PLAYA;
			}else if (temperatura>8){
				return TemperaturaStatus.CIUDAD;
			}else{
				return TemperaturaStatus.MONTANIA;
			}
		}
	}


	public enum  Status{
		PLAYA_SOLEADA,SELVA_NUBLADA,BALNEARIA_LLUVIOSA,CIUDAD_SOLEADA,CIUADAD_NUBLADA,
		CIUDAD_LLUVIOSA,MONTANIA_NEVADA,PUENTE_NUBLADO, TORMENTA_PAREJA,NOCHE_ESTRELLADA
		,VENTANA_LLUVIOSA;
		public static Status devolverStatus(TemperaturaStatus temperaturaStatus,Hora hora,Clima clima){
			if(hora==Hora.NOCHE){
				if(clima==Clima.LLUVIA){
					return VENTANA_LLUVIOSA;
				}
				return NOCHE_ESTRELLADA;
			}else{//dia
				if(clima==Clima.SOLEADA){
					if(temperaturaStatus==TemperaturaStatus.CIUDAD){
						return CIUDAD_SOLEADA;
					}
					if(temperaturaStatus==TemperaturaStatus.PLAYA){
						return PLAYA_SOLEADA;
					}
					return MONTANIA_NEVADA;
				}
				if(clima==Clima.NUBLADO){
					if(temperaturaStatus==TemperaturaStatus.CIUDAD){
						return CIUADAD_NUBLADA;
					}
					if(temperaturaStatus==TemperaturaStatus.PLAYA){
						return SELVA_NUBLADA;
					}
					return PUENTE_NUBLADO;
				}
				if(temperaturaStatus==TemperaturaStatus.CIUDAD){
					return CIUDAD_LLUVIOSA;
				}
				if(temperaturaStatus==TemperaturaStatus.PLAYA){
					return BALNEARIA_LLUVIOSA;
				}
				return TORMENTA_PAREJA;
			}
		}
	}

	private Hora hora;
	private TemperaturaStatus temperaturaStatus;
	private  Clima clima;
	private String condition;
	public Status status;
	private Float pressure;
	private Float temperature;
	private City city;
	private class City{
		public String name;
		public String code;
		public City(String name,String code){
			this.name=name;
			this.code=code;
		}
	}
    public String getCityName(){
        return city.name;
    }

    public String getCondition(){
        return condition;
    }
    public Float getPressure(){
        return pressure;
    }
    public Float getTemperature(){
        return temperature;
    }

	public Weather(String condition,Float pressure,Float temperature,String name,String code){
		changeStatus( condition, pressure, temperature, name, code);
	}
	private void changeStatus(String condition,Float pressure,Float temperature,String name,String code){
		city=new City(name,code);
		this.condition=condition;
		this.pressure=pressure;
		this.temperature=temperature;
		Calendar calendar=Calendar.getInstance();
		this.hora = Hora.devolverHora(calendar.HOUR);
		this.temperaturaStatus=TemperaturaStatus.devolverTemperatura(temperature);
		this.clima = Clima.devolverClime(condition);
		this.status=Status.devolverStatus(temperaturaStatus,hora,clima);
	}

}
