import { CommonModule } from '@angular/common';
import { Component, Input, OnInit, Optional } from '@angular/core';
import {
  ControlValueAccessor,
  FormControl,
  ControlContainer,
  NG_VALUE_ACCESSOR,
  ReactiveFormsModule,
  FormsModule 
} from '@angular/forms';


@Component({
  selector: 'app-custom-select',
  templateUrl: './custom-select.component.html',
  styleUrls: ['./custom-select.component.scss'],
  standalone: true,
  providers:[
    {
      provide: NG_VALUE_ACCESSOR,
      multi: true,
      useExisting: CustomSelectComponent
    }
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class CustomSelectComponent implements OnInit, ControlValueAccessor {
  @Input() size: string = 'w-64'; // Tamanho padrão
  @Input()
  type: string = '';
  @Input()
  formControlName: string = '';
  value: string = '';
  onChange = (value: string) => {};
  onTouched = () => {};
  formControl!: FormControl;
  constructor(@Optional() public container: ControlContainer) {}
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

  getFormControl() {
    this.formControl = this.container.control?.get(
      this.formControlName
    ) as FormControl;
  }
}
