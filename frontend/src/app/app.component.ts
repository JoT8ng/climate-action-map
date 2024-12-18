import { Component, OnInit } from '@angular/core';
import * as mapboxgl from 'mapbox-gl';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  map!: mapboxgl.Map;

  ngOnInit() {
    this.map = new mapboxgl.Map({
      accessToken: import.meta.env.NG_APP_MAPBOX_TOKEN,
      container: 'map',
      style: 'mapbox://styles/mapbox/streets-v11',
      center: [-79.377399, 43.700574],
      zoom: 11,
    });
  }
}