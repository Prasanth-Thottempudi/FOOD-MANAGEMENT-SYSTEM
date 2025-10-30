import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { CommonServicesService } from 'src/app/services/common-services.service';

@Component({
  selector: 'app-reciepes',
  templateUrl: './reciepes.component.html',
  styleUrls: ['./reciepes.component.css'],
})
export class ReciepesComponent {
  recipes: any[] = [];

  userId: any;

  newRecipe: FormGroup;

  constructor(
    private route: Router,
    private fb: FormBuilder,
    private http: CommonServicesService
  ) {}
  ngOnInit(): void {
    this.userId = localStorage.getItem('id');
    this.http.getAllRecipesByBusinessId(this.userId).subscribe(
      (response) => {
        this.recipes = response;
      },
      (error) => {
        alert('something went wrong in reciepes' + error);
      }
    );
  }

  addRecipe() {}

  // View recipe details
  viewRecipe(recipe: any) {
    alert(`Viewing recipe: ${recipe.name}`);
  }

  // Open the Add Recipe Modal
  openAddRecipeModal() {
    alert('Add new recipe form');
  }

  // Edit the selected recipe
  editRecipe(recipe: any) {
    alert(`Editing recipe: ${recipe.name}`);
  }

  // Delete a recipe
  deleteRecipe(id: number) {
    // this.recipes = this.recipes.filter(recipe => recipe.id !== id);
    // alert('Recipe deleted successfully');
  }
}
