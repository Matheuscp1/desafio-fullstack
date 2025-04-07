import { CommonModule } from '@angular/common';
import { Component, Input, OnInit, Optional } from '@angular/core';
import { ControlContainer, ControlValueAccessor, FormControl, NG_VALUE_ACCESSOR, ReactiveFormsModule,FormsModule } from '@angular/forms';

@Component({
  selector: 'app-custom-input',
  templateUrl: './custom-input.component.html',
  styleUrls: ['./custom-input.component.scss'],
  providers:[
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: CustomInputComponent
    }
  ],
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class CustomInputComponent implements OnInit, ControlValueAccessor {

  typeError:any = {
    required: "Campo Obrigatorio",
    email: "Email invalido"
  }
  @Input() disabled:boolean = false; // Tamanho padrão

  @Input() size: string = 'w-64'; // Tamanho padrão

  @Input()
  type: string = ''

  @Input()
  formControlName: string = ''

  @Input()
  placeholder: string = ''
  

  value: string = ''
  onChange = (value:string) => {};
  onTouched = () => {};

  formControl!: FormControl

  constructor(@Optional() public container: ControlContainer) { }

  writeValue(obj: any): void {
    this.value = obj;
  }
  registerOnChange(fn: any): void {
    this.onChange = fn;
  }
  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  ngOnInit(): void {
    this.getFormControl();
  }

  getFormControl(){
    this.formControl = this.container.control?.get(this.formControlName) as FormControl
  }

   getTypeError(error:string){
    return this.typeError[error]
  }
}
