import { Component } from '@angular/core';
import {Message} from 'primeng/primeng';

@Component({
  selector: 'public',
    templateUrl: './public.component.html',
})
export class PublicComponent {
    public msgs: Message[] = [];
}
