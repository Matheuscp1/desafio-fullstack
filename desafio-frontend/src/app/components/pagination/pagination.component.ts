import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import {
  Component,
  EventEmitter,
  Input,
  Output,
  ChangeDetectorRef,
  OnChanges,
  SimpleChanges,
} from '@angular/core';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.scss'],
  standalone: true,
  imports: [CommonModule],
})
export class PaginationComponent implements OnChanges {
  @Input() currentPage!: number;
  @Input() totalPages!: number;
  @Output() pageChange = new EventEmitter<{ page: number; size: number }>();
  @Input() size: number = 5;
  numberPages!: number[];
  numberSize: number[] = [5, 10, 50, 100];
  openTotal: boolean = false;
  constructor(private cdr: ChangeDetectorRef) {}
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['totalPages'])
      this.numberPages = [
        ...Array(changes['totalPages'].currentValue).keys(),
      ].map((foo) => foo + 1);
  }

  ngOnInit() {}

  generatePages(): number[] {
    const pages: number[] = [];
    const maxPagesToShow = 20;
    let startPage = Math.max(
      this.currentPage - Math.floor(maxPagesToShow / 2),
      1
    );
    let endPage = startPage + maxPagesToShow - 2;
    if (endPage > this.totalPages) {
      endPage = this.totalPages;
      startPage = Math.max(endPage - maxPagesToShow + 1, 1);
    }
    for (let i = startPage; i <= endPage; i++) {
      pages.push(i);
    }
    return pages;
  }

  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.pageChange.emit({ page: this.currentPage + 1, size: this.size });
    }
  }

  prevPage() {
    if (this.currentPage > 0) {
      this.pageChange.emit({ page: this.currentPage - 1, size: this.size });
    }
  }

  changeSize(size: number) {
    this.pageChange.emit({ page: 0, size: size });
  }
}
