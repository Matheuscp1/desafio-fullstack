import { CommonModule } from '@angular/common';
import { Component, Input, OnInit, Optional } from '@angular/core';
import { ControlContainer, ControlValueAccessor, FormControl, NG_VALUE_ACCESSOR, ReactiveFormsModule,FormsModule } from '@angular/forms';

@Component({
  selector: 'app-custom-textearea',
  templateUrl: './custom-textearea.component.html',
  styleUrls: ['./custom-textearea.component.scss'],
  providers:[
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: CustomTextAreaComponent
    }
  ],
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class CustomTextAreaComponent implements OnInit, ControlValueAccessor {

  typeError:any = {
    required: "Campo Obrigatorio",
  }

  @Input()
  formControlName: string = ''

  @Input()
  placeholder: string = ''

  @Input()
  rows: number = 1

  @Input()
  cols: number = 1
  

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
