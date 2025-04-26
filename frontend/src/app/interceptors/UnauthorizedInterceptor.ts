import { Router } from '@angular/router';
import { inject, Injectable } from '@angular/core';
import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { catchError, Observable, tap, throwError } from 'rxjs';

@Injectable()
export class UnauthorizedInterceptor implements HttpInterceptor {
  constructor(private router: Router) {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (localStorage.getItem('auth')) {
      req = req.clone({
        setHeaders: {
          Authorization: `Basic ${localStorage.getItem('auth')}`
        }
      })
    }
    return next.handle(req).pipe(
      catchError((err: HttpErrorResponse) => {
        if (err.status === 403 || err.status === 401 || err.status === 500) {
          localStorage.removeItem('auth');
          if (this.router.url !== '/login') {
            this.router.navigate(['/login']);
          }
        }
        throw new Error(err.message);
      })
    );
  }
}