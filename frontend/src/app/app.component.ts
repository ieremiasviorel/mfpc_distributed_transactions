import { Component } from '@angular/core';
import { MatTabChangeEvent } from '@angular/material/tabs';
import { Router } from '@angular/router';

import { NAVIGATION } from './model/constant';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: [ './app.component.scss' ]
})
export class AppComponent {
  title = 'frontend';

  selectedTabIndex = 0;

  tabNavigation = [ NAVIGATION.AIRPORT, NAVIGATION.FLIGHT, NAVIGATION.RESERVATION ];

  constructor(private router: Router) {
    this.router.navigate([ this.tabNavigation[0], NAVIGATION.LIST ]);
  }

  async handleTabChange(tabChangeEvent: MatTabChangeEvent): Promise<void> {
    this.selectedTabIndex = tabChangeEvent.index;
    await this.router.navigate([ this.tabNavigation[tabChangeEvent.index], NAVIGATION.LIST ]);
  }
}
