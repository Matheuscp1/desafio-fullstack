import { AuthService } from './../../services/authenticantion/auth.service';
import { CustomInputComponent } from './../custom-input/custom-input.component';
import { RouterModule } from '@angular/router';
import { LocalService } from './../../services/authenticantion/local-service.service';
import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule,
    CustomInputComponent
  ],
})
export class LoginComponent {
  form!: FormGroup;
  hasError:string = ''

  constructor(private fb: FormBuilder, private authService: AuthService,
    private localStore: LocalService,private router: Router) { }

  ngOnInit(): void {
    this.construirForm();
  }

  construirForm(){

    const validate = (valueExpected: string): ValidatorFn =>
    (control: AbstractControl): ValidationErrors | null =>
      control.value === valueExpected ? null : { valueExpected }

    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]],
    });
  }

  getFormControl(controlName: string){
    return this.form.get(controlName) as FormControl
  }

  submit(){
    if(this.form.valid){
      this.authService.login(this.form).subscribe({
        next: (value) => {
          this.localStore.saveData('session', value.token);
          console.log('ss',this.localStore.decodeToken())

          setTimeout(() => {
            this.router.navigateByUrl('/');
          }, 500);
        },
        error: (err) => {
          console.log('error aqui', err)
          this.hasError = err.error.errors[0]
        }
      })
    }else{
      this.form.markAllAsTouched()
    }
    
  }

 }
